package com.galaxy.novelit.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResDTO {
	private String nickname;
	private String accessToken;
	private String refreshToken;
}
