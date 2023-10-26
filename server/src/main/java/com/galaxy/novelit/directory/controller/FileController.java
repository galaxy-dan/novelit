package com.galaxy.novelit.directory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.galaxy.novelit.directory.dto.response.FileResDTO;
import com.galaxy.novelit.directory.service.DirectoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
	private final DirectoryService directoryService;

	@GetMapping
	public ResponseEntity<FileResDTO> getFile(@RequestParam String uuid){
		return ResponseEntity.ok(directoryService.getFile(uuid, "123"));
	}
}
