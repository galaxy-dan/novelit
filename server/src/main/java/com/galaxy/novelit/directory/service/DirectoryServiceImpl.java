package com.galaxy.novelit.directory.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxy.novelit.common.exception.AccessRefusedException;
import com.galaxy.novelit.common.exception.NoSuchDirectoryException;
import com.galaxy.novelit.common.exception.WrongDirectoryTypeException;
import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.dto.request.DirectoryCreateReqDTO;
import com.galaxy.novelit.directory.dto.request.DirectoryNameEditReqDTO;
import com.galaxy.novelit.directory.dto.request.FileWorkReqDTO;
import com.galaxy.novelit.directory.dto.response.DirectoryCreateResDTO;
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
	public DirectoryCreateResDTO createDirectory(DirectoryCreateReqDTO dto, String userUUID) {
		/* dto의 workspaceUUID로 작품을 얻어오고 작가의 uuid와 인자로 받은 userUUID 비교해야함


		if(userUUID != ){

		}
		*/
		String parentUUID = dto.getParentUUID();
		boolean isDirectory = dto.isDirectory();

		Directory parent = directoryRepository.findByUuidAndDeleted(parentUUID, false);
		//부모 예외 처리
		if(parentUUID != null){
			checkDirectoryException(parent, userUUID);
		}

		String directoryUUID = UUID.randomUUID().toString();
		Directory.DirectoryBuilder builder = Directory.builder()
			.uuid(directoryUUID)
			.name(dto.getName())
			.directory(isDirectory)
			.parentUUID(parentUUID)
			.workspaceUUID(dto.getWorkspaceUUID())
			.userUUID(userUUID)
			.deleted(false);
		if(isDirectory){
			builder.children(new ArrayList<>());
		}else{
			builder.content("");
		}
		Directory directory = builder.build();

		directoryRepository.save(directory);

		//상위 디렉토리 있으면 children으로 추가
		if(parentUUID != null) {
			parent.getChildren().add(directory);
			directoryRepository.save(parent);
		}
		return new DirectoryCreateResDTO(directoryUUID);
	}

	@Transactional
	@Override
	public void editDirectoryName(DirectoryNameEditReqDTO dto, String userUUID) {
		/* dto의 uuid로 디렉토리의 uuid 얻어오고 이로 작품 uuid 얻고, 작가의 uuid와 인자로 받은 userUUID 비교해야함 (DB 2번)
			=> 디렉토리 테이블 내에 작가 uuid 박아넣으면 DB 조회 1번으로 가능? 나중에 생각

		if(userUUID != ){

		}
		*/
		Directory directory = directoryRepository.findByUuidAndDeleted(dto.getUuid(), false);

		checkDirectoryException(directory, userUUID);

		directory.editName(dto.getName());
		directoryRepository.save(directory);
	}

	@Transactional(readOnly = true)
	@Override
	public DirectoryResDTO getDirectory(String directoryUUID, String userUUID) {
		/* dto의 uuid로 디렉토리의 uuid 얻어오고 이로 작품 uuid 얻고, 작가의 uuid와 인자로 받은 userUUID 비교해야함

		if(userUUID != ){

		}
		*/

		Directory directory = directoryRepository.findByUuidAndDeleted(directoryUUID, false);
		//예외 처리
		checkDirectoryException(directory, userUUID);
		if(!directory.isDirectory()){
			throw new WrongDirectoryTypeException();
		}

		//자식 디렉토리와 파일 따로 분류
		List<Directory> children = directory.getChildren();
		List<DirectorySimpleElementDTO> directories = children.stream()
			.filter(child -> child.isDirectory() && !child.isDeleted())
			.map(child -> new DirectorySimpleElementDTO(child.getUuid(), child.getName()))
			.toList();
		List<DirectorySimpleElementDTO> files = children.stream()
			.filter(child -> !child.isDirectory() && !child.isDeleted())
			.map(child -> new DirectorySimpleElementDTO(child.getUuid(), child.getName()))
			.toList();
		return new DirectoryResDTO(directories, files);

	}

	@Transactional(readOnly = true)
	@Override
	public FileResDTO getFile(String directoryUUID, String userUUID) {
		Directory directory = directoryRepository.findByUuidAndDeleted(directoryUUID, false);
		//예외 처리
		checkDirectoryException(directory, userUUID);
		if(directory.isDirectory()){
			throw new WrongDirectoryTypeException();
		}

		return new FileResDTO(directory.getName(), directory.getContent());
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
			Queue<Directory> queue = new ArrayDeque<>();
			queue.add(directory);

			while (!queue.isEmpty()) {
				Directory cur = queue.poll();
				List<Directory> children = cur.getChildren();
				for (Directory child : children) {
					//이미 삭제된 애들은 무시
					if (child.isDeleted()) {
						continue;
					}
					deletedDirectories.add(child.getUuid());
					if (child.isDirectory()) {
						queue.add(child);
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
		checkDirectoryException(directory, userUUID);
		if(directory.isDirectory()){
			throw new WrongDirectoryTypeException();
		}

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
