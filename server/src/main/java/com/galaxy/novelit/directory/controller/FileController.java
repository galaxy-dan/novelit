package com.galaxy.novelit.directory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.galaxy.novelit.directory.dto.request.FileWorkReqDTO;
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
		return ResponseEntity.ok(directoryService.getFile(uuid, "f72a8efc-99dc-4afd-a658-6f42073fb7a3"));
	}

	@PatchMapping
	public ResponseEntity<Void> workFile(@RequestBody FileWorkReqDTO dto){
		directoryService.workFile(dto,"f72a8efc-99dc-4afd-a658-6f42073fb7a3");
		return ResponseEntity.ok().build();
	}
}
