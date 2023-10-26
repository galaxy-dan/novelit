package com.galaxy.novelit.directory.controller;

import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Void> createDirectory(@RequestBody DirectoryCreateReqDTO dto){
		directoryService.createDirectory(dto, "123");
		return ResponseEntity.ok().build();
	}

	@PatchMapping
	public ResponseEntity<Void> editDirectoryName(@RequestBody DirectoryNameEditReqDTO dto){
		directoryService.editDirectoryName(dto, "123");
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<DirectoryResDTO> getDirectory(@RequestParam String uuid){
		return ResponseEntity.ok(directoryService.getDirectory(uuid, "123"));
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteDirectory(String uuid){
		directoryService.deleteDirectory(uuid, "123");
		return ResponseEntity.ok().build();
	}
}