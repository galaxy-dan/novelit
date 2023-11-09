package com.galaxy.novelit.config.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.galaxy.novelit.auth.util.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final JwtUtils jwtUtils;
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws ServletException, IOException {
		String accessToken = resolveToken(request);
		if (accessToken != null && jwtUtils.validateToken(accessToken)) {
			Authentication authentication = jwtUtils.getAuthentication(accessToken);
			System.out.println(authentication.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer")) {
			return accessToken.substring(7);
		}
		return null;
	}


}
