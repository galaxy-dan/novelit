package com.galaxy.novelit.auth.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String KAKAO_CLIENT_ID;

	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String KAKAO_CLIENT_SECRET;

	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String KAKAO_REDIRECT_URL;

	@Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
	private String KAKAO_TOKEN_URL;

	@Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
	private String KAKAO_INFO_URL;


	@Value("${jwt.secret}")
	private String secretKey;

	private final RestTemplate restTemplate = new RestTemplateBuilder()
		.uriTemplateHandler(new DefaultUriBuilderFactory())
		.build();

	private final UserRepository userRepository;
	private final JwtUtils jwtUtils;
	//private final AuthenticationManager authenticationManager;
	@Override
	public LoginResDTO kakaoLogin(String code) {
		KaKaoAccessTokenDTO kakaoAccessToken = getAccessToken(code);
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

		Authentication authenticate = new UsernamePasswordAuthenticationToken(userUUID, "", List.of(() -> "USER"));
		String accessToken = jwtUtils.generateAccessToken(authenticate);
		String refreshToken = jwtUtils.generateRefreshToken(authenticate);

		return new LoginResDTO(nickname, accessToken, refreshToken);
	}

	private KaKaoAccessTokenDTO getAccessToken(String code) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", KAKAO_CLIENT_ID);
		params.add("client_secret", KAKAO_CLIENT_SECRET);
		params.add("redirect_uri", KAKAO_REDIRECT_URL);
		params.add("code", code);


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Accept", "application/json");

		HttpEntity<Object> entity = new HttpEntity<>(params, headers);


		return restTemplate.postForObject(KAKAO_TOKEN_URL, entity, KaKaoAccessTokenDTO.class);
	}

	private KakaoUserInfoDTO getKakaoUserInfo(KaKaoAccessTokenDTO kakaoAccessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + kakaoAccessToken.getAccessToken());

		HttpEntity<Object> entity = new HttpEntity<>(headers);

		return restTemplate.postForObject(KAKAO_INFO_URL, entity, KakaoUserInfoDTO.class);
	}
}
