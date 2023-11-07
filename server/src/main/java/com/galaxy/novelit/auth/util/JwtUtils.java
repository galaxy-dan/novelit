package com.galaxy.novelit.auth.util;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.galaxy.novelit.common.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	private final Key key;
	@Value("${jwt.access-token-expire}")
	private long accessTokenDuration;
	@Value("${jwt.refresh-token-expire}")
	private long refreshTokenDuration;

	public JwtUtils(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateAccessToken(Authentication authentication) {
		String userUUID = authentication.getPrincipal().toString();
		String roles = getMemberRoles(authentication);
		return generateToken(userUUID, accessTokenDuration, roles);
	}

	public String generateRefreshToken(Authentication authentication) {
		String userUUID = authentication.getPrincipal().toString();
		String roles = getMemberRoles(authentication);

		return generateToken(userUUID, refreshTokenDuration, roles);
	}


	private String generateToken(String userUUID, long duration, String roles) {
		Date issuedTime = new Date();
		Date expiredTime = new Date(issuedTime.getTime() + duration);

		return Jwts.builder()
			.claim("id", userUUID)
			.claim("role", roles)
			.setIssuedAt(issuedTime)
			.setExpiration(expiredTime)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String generateShareToken(String directoryUUID, long duration) {
		Date issuedTime = new Date();
		Date expiredTime = new Date(issuedTime.getTime() + duration);

		return Jwts.builder()
			.claim("id", directoryUUID)
			.setIssuedAt(issuedTime)
			.setExpiration(expiredTime)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	private String getMemberRoles(Authentication authentication) {
		List<String> authorities = authentication.getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());
		return String.join(",", authorities);
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = Jwts
			.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(accessToken)
			.getBody();

		String userUUId = (String) claims.get("id");
		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get("role").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.toList();
		return new UsernamePasswordAuthenticationToken(userUUId, "", authorities);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			throw new InvalidTokenException("잘못된 JWT 시그니처");
		} catch (UnsupportedJwtException e) {
			throw new InvalidTokenException("유효하지 않은 JWT 토큰");
		} catch (ExpiredJwtException e) {
			throw new InvalidTokenException("토큰 기한 만료");
		} catch (IllegalArgumentException e) {
			throw new InvalidTokenException("JWT token compact of handler are invalid.");
		}

	}
}
