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

import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.domain.File;
import com.galaxy.novelit.directory.dto.request.DirectoryCreateReqDTO;
import com.galaxy.novelit.directory.dto.request.DirectoryNameEditReqDTO;
import com.galaxy.novelit.directory.dto.response.DirectoryResDTO;
import com.galaxy.novelit.directory.dto.response.DirectorySimpleElementDTO;
import com.galaxy.novelit.directory.dto.response.FileResDTO;
import com.galaxy.novelit.directory.repository.DirectoryRepository;
import com.galaxy.novelit.directory.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService{
	private final DirectoryRepository directoryRepository;
	private final FileRepository fileRepository;
	private final MongoTemplate mongoTemplate;
	@Transactional
	@Override
	public void createDirectory(DirectoryCreateReqDTO dto, String userUUID) {
		/* dto의 workspaceUUID로 작품을 얻어오고 작가의 uuid와 인자로 받은 userUUID 비교해야함


		if(userUUID != ){

		}
		*/
		String parentUUID = dto.getParentUUID();

		String directoryUUID = UUID.randomUUID().toString();
		Directory directory = Directory.builder()
			.uuid(directoryUUID)
			.name(dto.getName())
			.directory(dto.isDirectory())
			.parentUUID(parentUUID)
			.children(new ArrayList<>())
			.workspaceUUID(dto.getWorkspaceUUID())
			.deleted(false)
			.build();

		directoryRepository.save(directory);

		//상위 디렉토리 있으면 children으로 추가
		if(parentUUID != null) {
			Directory parent = directoryRepository.findByUuidAndDeleted(parentUUID, false);
			parent.getChildren().add(directory);
			directoryRepository.save(parent);
		}


		//디렉토리가 아니라 파일인 경우 파일 테이블에 따로 데이터 생성
		if(!dto.isDirectory()){
			File file = File.builder()
				.content("")
				.directoryUUID(directoryUUID)
				.deleted(false)
				.build();

			fileRepository.save(file);
		}

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
		/* 404 예외 처리

		*/

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
		/* dto의 uuid로 디렉토리의 uuid 얻어오고 이로 작품 uuid 얻고, 작가의 uuid와 인자로 받은 userUUID 비교해야함

		if(userUUID != ){

		}
		*/

		File file = fileRepository.findByDirectoryUUIDAndDeleted(directoryUUID, false);
		return new FileResDTO(directory.getName(), file.getContent());
	}

	@Transactional
	@Override
	public void deleteDirectory(String directoryUUID, String userUUID) {
		/* dto의 uuid로 디렉토리의 uuid 얻어오고 이로 작품 uuid 얻고, 작가의 uuid와 인자로 받은 userUUID 비교해야함

		if(userUUID != ){

		}
		*/

		Directory directory = directoryRepository.findByUuidAndDeleted(directoryUUID, false);
		if(directory != null){
			//children 모두 삭제
			List<String> deletedDirectories = new ArrayList<>();
			List<String> deletedFiles = new ArrayList<>();

			//bfs로 완전 하위 디렉토리 완전 탐색
			Queue<Directory> queue = new ArrayDeque<>();
			queue.add(directory);

			//파일이면 디렉토리랑 파일 db까지 deleted 표시
			if(!directory.isDirectory()){
				deletedFiles.add(directory.getUuid());
			}
			deletedDirectories.add(directory.getUuid());

			while(!queue.isEmpty()) {
				Directory cur = queue.poll();
				List<Directory> children = cur.getChildren();
				for(Directory child : children){
					//이미 삭제된 애들은 무시
					if(child.isDeleted()){
						continue;
					}
					if(!child.isDirectory()){
						deletedFiles.add(child.getUuid());
					}
					deletedDirectories.add(child.getUuid());
					queue.add(child);
				}
			}

			//directory와 file 수정 작업
			mongoTemplate.updateMulti(
				Query.query(Criteria.where("uuid").in(deletedDirectories)),
				Update.update("deleted", true),
				Directory.class
			);
			fileRepository.updateDeleted(deletedFiles, true);
		}
	}



}
