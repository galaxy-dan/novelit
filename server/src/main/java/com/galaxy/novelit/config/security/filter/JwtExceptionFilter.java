package com.galaxy.novelit.config.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		ServletException, IOException {
		try {
			chain.doFilter(request, response); // JwtFilter로 이동
		} catch (JwtException ex) {
			// JwtFilter에서 예외 발생하면 바로 setErrorResponse 호출
			setErrorResponse(request, response, ex);
		}
	}

	public void setErrorResponse(HttpServletRequest req, HttpServletResponse res, Throwable ex) throws IOException {
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);

		final Map<String, Object> body = new HashMap<>();

		if(ex.getMessage().equals("토큰 확인")) {
			res.setStatus(HttpStatus.BAD_REQUEST.value());
		} else {
			res.setStatus(HttpStatus.UNAUTHORIZED.value());
		}


		// ex.getMessage() 에는 jwtException을 발생시키면서 입력한 메세지가 들어있다.
		body.put("message", ex.getMessage());
		final ObjectMapper mapper = new ObjectMapper();

		mapper.writeValue(res.getOutputStream(), body);
	}
}

