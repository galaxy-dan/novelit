package com.galaxy.novelit.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.galaxy.novelit.auth.dto.response.LoginResDTO;
import com.galaxy.novelit.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService kakaoOAuthService;

	//구글 로그인 성공 후 인가 코드로 자체 로그인 작업
	@GetMapping("/login/oauth2/kakao")
	public ResponseEntity<LoginResDTO> googleLogin(@RequestParam String code) throws JsonProcessingException {
		return ResponseEntity.ok(kakaoOAuthService.kakaoLogin(code));
	}
}
