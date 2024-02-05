package com.mju.mentoring.exam.board.provider;

import java.sql.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private static final long ACCESS_EXPIRED_TIME = 1000 * 60 * 30;            // 30분

	public static SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	public String createJwtAccessToken(String userId) {
		Claims claims = Jwts.claims()
			.setSubject("access_token");
		claims.put("user_id", userId);
		String jwt = Jwts.builder()
			.addClaims(claims)
			.setExpiration(
				new Date(System.currentTimeMillis() + ACCESS_EXPIRED_TIME)
			)
			.setIssuedAt(new java.util.Date())
			.signWith(SignatureAlgorithm.HS512, SECRET)
			.setIssuer("manager")
			.compact();
		return jwt;
	}
}
