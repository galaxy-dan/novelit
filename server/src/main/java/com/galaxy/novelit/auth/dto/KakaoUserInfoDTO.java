package com.galaxy.novelit.auth.dto;

import java.util.Map;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserInfoDTO {
	private Map<String, Object> kakaoAccount;
	private Map<String, String> properties;
	// @Getter
	// @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	// public static class KakaoAccount {
	// 	private String email;
	// 	private String nickname;
	// }
}
