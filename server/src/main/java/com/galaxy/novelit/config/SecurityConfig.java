package com.galaxy.novelit.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.galaxy.novelit.config.security.filter.JwtExceptionFilter;
import com.galaxy.novelit.config.security.filter.JwtFilter;
import com.galaxy.novelit.config.security.handler.CustomAccessDeniedHandler;
import com.galaxy.novelit.config.security.handler.CustomAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity	// prepostenabled = true (default), @EnableGlobalMethodSecurity deprecated
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtFilter jwtFilter;
	private final JwtExceptionFilter jwtExceptionFilter;


	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			// http basic을 통한 인증은 하지 않으므로 설정 해제
			.httpBasic(AbstractHttpConfigurer::disable)

			// Rest API 서버 이므로 csrf 관련 설정은 사용하지 않는다.
			.csrf(AbstractHttpConfigurer::disable)

			// 사용자 로그인이 필요한 API는 필터가 적용되도록 별도 설정해준다.
			.authorizeHttpRequests(request
							-> request.requestMatchers(
									"/",
									"/actuator/**",
									"/login/**",
									"/util/**",
									"/plot/**"
							).permitAll()
							.requestMatchers(HttpMethod.GET,"/file").hasAnyAuthority("USER","EDITOR")
							.requestMatchers(HttpMethod.PATCH,"/file").hasAnyAuthority("USER","EDITOR")
							//.requestMatchers("/comment").hasAnyAuthority("USER","EDITOR")
							//.requestMatchers("/notifications/**").hasAnyAuthority("USER","EDITOR")
							.requestMatchers("/share/token/validation").permitAll()
							.anyRequest().permitAll()
					//.anyRequest().hasAuthority("USER")


			)

			// http Session을 사용하지 않을 것이므로 Policy를 stateless로 설정한다.
			.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			// 인증이 필요한 서비스의 경우 Authorization Header에 Bearer토큰 여부와, 토큰 유효 여부를 판단해야하므로 커스텀 필터를 추가한다.
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtExceptionFilter, JwtFilter.class)
			// Filter에서 발생한 예외는 Controller Advice를 통해 제어할 수 없으므로 인증/인가 실패 관련 오류 제어를 위한 객체를 추가한다.
			.exceptionHandling(c -> c.authenticationEntryPoint(new CustomAuthenticationEntryPoint()).accessDeniedHandler(new CustomAccessDeniedHandler()));

		return http.build();
	}
}
