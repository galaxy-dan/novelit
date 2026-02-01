package com.galaxy.novelit.directory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.galaxy.novelit.directory.dto.request.DirectoryCreateReqDTO;
import com.galaxy.novelit.directory.dto.request.DirectoryNameEditReqDTO;
import com.galaxy.novelit.directory.dto.response.DirectoryResDTO;
import com.galaxy.novelit.directory.service.DirectoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/directory")
@RequiredArgsConstructor
public class DirectoryController {
	private final DirectoryService directoryService;

	@PostMapping
	public ResponseEntity<Void> createDirectory(@RequestBody DirectoryCreateReqDTO dto, String uuid){
		directoryService.createDirectory(dto, uuid);
		return ResponseEntity.ok().build();
	}

	@PatchMapping
	public ResponseEntity<Void> editDirectoryName(@RequestBody DirectoryNameEditReqDTO dto, Authentication authentication){
		directoryService.editDirectoryName(dto, authentication.getName());
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<DirectoryResDTO> getDirectory(@RequestParam String uuid, Authentication authentication){
		return ResponseEntity.ok(directoryService.getDirectory(uuid, authentication.getName()));
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteDirectory(String uuid, Authentication authentication){
		directoryService.deleteDirectory(uuid, authentication.getName());
		return ResponseEntity.ok().build();
	}
}