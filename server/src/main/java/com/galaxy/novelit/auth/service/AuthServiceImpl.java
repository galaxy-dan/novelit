package com.galaxy.novelit.auth.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.galaxy.novelit.auth.dto.KaKaoAccessTokenDTO;
import com.galaxy.novelit.auth.dto.KakaoUserInfoDTO;
import com.galaxy.novelit.auth.dto.response.LoginResDTO;
import com.galaxy.novelit.auth.util.JwtUtils;
import com.galaxy.novelit.author.domain.User;
import com.galaxy.novelit.author.repository.UserRepository;
import com.galaxy.novelit.common.exception.InvalidTokenException;
import com.galaxy.novelit.common.exception.NotLoggedInException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String KAKAO_CLIENT_ID;

	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String KAKAO_CLIENT_SECRET;

	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String KAKAO_REDIRECT_URI;

	@Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
	private String KAKAO_TOKEN_URL;

	@Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
	private String KAKAO_INFO_URL;

	private final RestTemplate restTemplate = new RestTemplateBuilder()
		.uriTemplateHandler(new DefaultUriBuilderFactory())
		.build();

	private final UserRepository userRepository;
	private final JwtUtils jwtUtils;
	private final RedisTemplate<String, String> redisTemplate;
	@Override
	public LoginResDTO kakaoLogin(String code) {
		// 카카오 엑세스 토큰을 요청하는 함수
		KaKaoAccessTokenDTO kakaoAccessToken = getAccessToken(code);
		// 카카오 유저정보를 요청하는 함수
		KakaoUserInfoDTO kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken);
		String email = (String)kakaoUserInfo.getKakaoAccount().get("email");
		String nickname = kakaoUserInfo.getProperties().get("nickname");

		User user = userRepository.findByEmail(email);
		String userUUID;
		// 가입됨 = 로그인
		if(user != null){
			userUUID = user.getUserUUID();
		}else{ // 최초 로그인 = 회원가입
			userUUID = UUID.randomUUID().toString();
			userRepository.save(
				User.builder()
					.userUUID(userUUID)
					.email(email)
					.nickname(nickname)
					.build());
		}

		// 유저정보를 저장하고 Authentication을 발급받음.
		Authentication authenticate = new UsernamePasswordAuthenticationToken(userUUID, "", List.of(() -> "USER"));
		// SecurityContextHolder에 인증정보를 저장하고, accessToken, RefreshToken을 생성하여 redis에 저장
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String accessToken = jwtUtils.generateAccessToken(authenticate);
		String refreshToken = jwtUtils.generateRefreshToken(authenticate);

		redisTemplate.opsForValue().set(
			userUUID,
			refreshToken,
			jwtUtils.getRefreshTokenDuration(),
			TimeUnit.MILLISECONDS
		);

		// API로 요청한 accessToken, RefreshToken 정보를 리턴
		return new LoginResDTO(accessToken, refreshToken);
	}


	private KaKaoAccessTokenDTO getAccessToken(String code) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", KAKAO_CLIENT_ID);
		params.add("client_secret", KAKAO_CLIENT_SECRET);
		params.add("redirect_uri", KAKAO_REDIRECT_URI);
		params.add("code", code);


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Accept", "application/json");

		HttpEntity<Object> entity = new HttpEntity<>(params, headers);


		return restTemplate.postForObject(KAKAO_TOKEN_URL, entity, KaKaoAccessTokenDTO.class);
	}

	private KakaoUserInfoDTO getKakaoUserInfo(KaKaoAccessTokenDTO kakaoAccessToken) {
		HttpHeaders headers = new HttpHeaders();

		// 카카오에서 발급받은 엑세스토큰에 Authorization, Bearer을 추가해 해더에 셋팅.
		headers.set("Authorization", "Bearer " + kakaoAccessToken.getAccessToken());

		HttpEntity<Object> entity = new HttpEntity<>(headers);

		// 그대로 restTemplate를 사용해서 KAKAO_INFO_URL로 요청 때림. 카카오 유저정보를 받아옴.
		return restTemplate.postForObject(KAKAO_INFO_URL, entity, KakaoUserInfoDTO.class);
	}


	@Override
	public LoginResDTO reissue(String refreshToken, String userUUID) {
		String refreshTokenInRedis = redisTemplate.opsForValue().get(userUUID);
		if(refreshTokenInRedis == null){
			throw new NotLoggedInException();
		}
		if(!refreshToken.equals(refreshTokenInRedis)){
			throw new InvalidTokenException("리프레시 토큰 불일치");
		}

		String newAccessToken = jwtUtils.generateAccessToken(userUUID);
		String newRefreshToken = jwtUtils.generateRefreshToken(userUUID);

		redisTemplate.opsForValue().set(
			userUUID,
			newRefreshToken,
			jwtUtils.getRefreshTokenDuration(),
			TimeUnit.MILLISECONDS
		);
		return new LoginResDTO(newAccessToken, newRefreshToken);
	}

	@Override
	public void logout(String userUUID) {
		redisTemplate.delete(userUUID);
	}
}
