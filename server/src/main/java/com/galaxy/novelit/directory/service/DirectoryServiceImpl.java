package com.galaxy.novelit.directory.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.domain.File;
import com.galaxy.novelit.directory.dto.request.DirectoryCreateReqDTO;
import com.galaxy.novelit.directory.dto.request.DirectoryNameEditReqDTO;
import com.galaxy.novelit.directory.repository.DirectoryRepository;
import com.galaxy.novelit.directory.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService{
	private final DirectoryRepository directoryRepository;
	private final FileRepository fileRepository;
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
			.build();

		directoryRepository.save(directory);

		//상위 디렉토리 있으면 children으로 추가
		if(parentUUID != null) {
			Directory parent = directoryRepository.findByUuid(parentUUID);
			parent.getChildren().add(directory);
			directoryRepository.save(parent);
		}


		//디렉토리가 아니라 파일인 경우 파일 테이블에 따로 데이터 생성
		if(!dto.isDirectory()){
			File file = File.builder()
				.content("")
				.directoryUUID(directoryUUID)
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
		Directory directory = directoryRepository.findByUuid(dto.getUuid());
		/* 404 예외 처리

		*/

		directory.editName(dto.getName());
		directoryRepository.save(directory);
	}
}
