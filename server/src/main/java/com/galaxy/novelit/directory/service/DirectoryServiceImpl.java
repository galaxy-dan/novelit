package com.galaxy.novelit.directory.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxy.novelit.common.exception.AccessRefusedException;
import com.galaxy.novelit.common.exception.IllegalUUIDException;
import com.galaxy.novelit.common.exception.NoSuchDirectoryException;
import com.galaxy.novelit.common.exception.WrongDirectoryTypeException;
import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.dto.request.DirectoryCreateReqDTO;
import com.galaxy.novelit.directory.dto.request.DirectoryNameEditReqDTO;
import com.galaxy.novelit.directory.dto.request.FileWorkReqDTO;
import com.galaxy.novelit.directory.dto.response.DirectoryResDTO;
import com.galaxy.novelit.directory.dto.response.DirectorySimpleElementDTO;
import com.galaxy.novelit.directory.dto.response.FileResDTO;
import com.galaxy.novelit.directory.repository.DirectoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService{
	private final DirectoryRepository directoryRepository;
	private final MongoTemplate mongoTemplate;
	@Transactional
	@Override
	public void createDirectory(DirectoryCreateReqDTO dto, String userUUID) {
		String workspaceUUID = dto.getWorkspaceUUID();
		String parentUUID = dto.getParentUUID();
		boolean isDirectory = dto.isDirectory();

		Directory parent = directoryRepository.findByUuidAndDeleted(parentUUID, false);
		checkDirectoryException(parent, userUUID);

		String directoryUUID = dto.getUuid();
		if(directoryUUID == null){
			throw new IllegalUUIDException();
		}

		Directory.DirectoryBuilder builder = Directory.builder()
			.uuid(directoryUUID)
			.name(dto.getName())
			.directory(isDirectory)
			.workspaceUUID(workspaceUUID)
			.parentUUID(parentUUID)
			.userUUID(userUUID)
			.deleted(false);
		if(isDirectory){
			builder.children(new ArrayList<>());
		}else{
			builder.content("").editable(true);
		}
		Directory directory = builder.build();

		directoryRepository.save(directory);

		//상위 디렉토리 children으로 추가
		parent.getChildren().add(directoryUUID);
		directoryRepository.save(parent);


	}

	@Transactional
	@Override
	public void editDirectoryName(DirectoryNameEditReqDTO dto, String userUUID) {
		Directory directory = directoryRepository.findByUuidAndDeleted(dto.getUuid(), false);

		checkDirectoryException(directory, userUUID);

		directory.editName(dto.getName());
		directoryRepository.save(directory);
	}

	@Transactional(readOnly = true)
	@Override
	public DirectoryResDTO getDirectory(String directoryUUID, String userUUID) {

		Directory directory = directoryRepository.findByUuidAndDeleted(directoryUUID, false);
		//예외 처리
		checkDirectoryException(directory, userUUID);
		if(!directory.isDirectory()){
			throw new WrongDirectoryTypeException();
		}

		//자식 디렉토리와 파일 따로 분류
		List<String> childrenUUIDs = directory.getChildren();
		List<Directory> children = directoryRepository.findAllByUuidInAndDeleted(childrenUUIDs, false);
		List<Directory> sortedChildren = childrenUUIDs.stream()
			.map(uuid -> children.stream()
				.filter(child -> uuid.equals(child.getUuid()))
				.findFirst()
				.orElse(null))
			.toList();

		List<DirectorySimpleElementDTO> directories = sortedChildren.stream()
			.filter(Directory::isDirectory)
			.map(child -> new DirectorySimpleElementDTO(child.getUuid(), child.getName()))
			.toList();
		List<DirectorySimpleElementDTO> files = sortedChildren.stream()
			.filter(child -> !child.isDirectory())
			.map(child -> new DirectorySimpleElementDTO(child.getUuid(), child.getName()))
			.toList();
		return new DirectoryResDTO(directories, files);

	}

	@Transactional(readOnly = true)
	@Override
	public FileResDTO getFile(String directoryUUID, String userUUID) {
		Directory directory = directoryRepository.findByUuidAndDeleted(directoryUUID, false);
		//예외 처리
		if(directoryUUID.equals(userUUID)){
			userUUID = directory.getUserUUID();
		}
		checkDirectoryException(directory, userUUID);
		if(directory.isDirectory()){
			throw new WrongDirectoryTypeException();
		}

		return new FileResDTO(directory.getName(), directory.getContent(), directory.isEditable());
	}

	@Transactional
	@Override
	public void deleteDirectory(String directoryUUID, String userUUID) {
		Directory directory = directoryRepository.findByUuidAndDeleted(directoryUUID, false);
		//예외 처리
		checkDirectoryException(directory, userUUID);

		//삭제할 uuid 저장용
		List<String> deletedDirectories = new ArrayList<>();
		deletedDirectories.add(directory.getUuid());

		//디렉토리라면 children도 삭제 필요
		if(directory.isDirectory()) {

			//bfs로 하위 디렉토리 완전 탐색
			Queue<String> queue = new ArrayDeque<>();
			queue.add(directoryUUID);

			while (!queue.isEmpty()) {
				String cur = queue.poll();
				List<Directory> children = directoryRepository.findAllByParentUUIDAndDeleted(cur, false);
				for (Directory child : children) {
					deletedDirectories.add(child.getUuid());
					if (child.isDirectory()) {
						queue.add(child.getUuid());
					}
				}
			}
		}

		mongoTemplate.updateMulti(
			Query.query(Criteria.where("uuid").in(deletedDirectories)),
			Update.update("deleted", true),
			Directory.class
		);

	}

	@Transactional
	@Override
	public void workFile(FileWorkReqDTO dto, String userUUID) {
		String directoryUUID = dto.getUuid();
		Directory directory = directoryRepository.findByUuidAndDeleted(directoryUUID, false);

		//예외 처리
		if(directoryUUID.equals(userUUID)){
			userUUID = directory.getUserUUID();
		}
		checkDirectoryException(directory, userUUID);
		if(directory.isDirectory()){
			throw new WrongDirectoryTypeException();
		}

		// if(!directory.isEditable()){
		// 	throw new EditRefusedException();
		// }

		directory.updateContent(dto.getContent());
		directoryRepository.save(directory);
	}

	//공통적인 directory 예외 처리
	private void checkDirectoryException(Directory directory, String userUUID){
		//404 예외 처리
		if(directory == null){
			throw new NoSuchDirectoryException();
		}

		//권한 예외 처리
		if(!directory.getUserUUID().equals(userUUID)){
			throw new AccessRefusedException();
		}
	}

}
