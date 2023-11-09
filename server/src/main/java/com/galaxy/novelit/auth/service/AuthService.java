package com.galaxy.novelit.auth.service;

import com.galaxy.novelit.auth.dto.response.LoginResDTO;

public interface AuthService {
	LoginResDTO kakaoLogin(String code);
}
