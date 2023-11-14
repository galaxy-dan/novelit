package com.galaxy.novelit.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.galaxy.novelit.auth.dto.response.LoginResDTO;
import com.galaxy.novelit.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	//구글 로그인 성공 후 인가 코드로 자체 로그인 작업
	@GetMapping("/login/oauth2/kakao")
	public ResponseEntity<LoginResDTO> googleLogin(@RequestParam String code) {
		return ResponseEntity.ok(authService.kakaoLogin(code));
	}

	@GetMapping("/login/reissue")
	public ResponseEntity<LoginResDTO> reissue(HttpServletRequest request,Authentication authentication) {
		return ResponseEntity.ok(authService.reissue(request.getHeader("authorization").substring(7), authentication.getName()));
	}

	@GetMapping("/login/logout")
	public ResponseEntity<Void> logout(Authentication authentication) {
		authService.logout(authentication.getName());
		return ResponseEntity.ok().build();
	}
}
