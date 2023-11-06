package com.galaxy.novelit.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KaKaoAccessTokenDTO {
	private String accessToken;
	private int expiresIn;
	private String refreshToken;
	private int refreshTokenExpiresIn;
}
