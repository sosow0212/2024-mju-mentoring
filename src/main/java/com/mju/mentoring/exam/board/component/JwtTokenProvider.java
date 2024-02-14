package com.mju.mentoring.exam.board.component;

import java.security.SignatureException;
import java.sql.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mju.mentoring.exam.board.domain.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	@Value("${access.expired_time}")
	private String ACCESS_EXPIRED_TIME;

	public static SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	public String createJwtAccessToken(Member member) {
		Claims claims = Jwts.claims()
			.setSubject("access_token");
		claims.put("id", member.getId());

		String jwt = Jwts.builder()
			.addClaims(claims)
			.setExpiration(
				new Date(System.currentTimeMillis() + Long.parseLong(ACCESS_EXPIRED_TIME))
			)
			.setIssuedAt(new java.util.Date())
			.signWith(SignatureAlgorithm.HS512, SECRET)
			.setIssuer("manager")
			.compact();
		return jwt;
	}

	public boolean validateToken(String token) throws SignatureException {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			if (checkExpiredTime(token)) {
				throw new Exception();
			}
		} catch (SignatureException | MalformedJwtException |
				 UnsupportedJwtException | IllegalArgumentException | ExpiredJwtException jwtException) {
		} catch (Exception e) {
		}
		return true;
	}

	public boolean checkExpiredTime(String token) {
		if ((new Date(System.currentTimeMillis()).getTime() - getExpiredTime(token).getTime()) > 30000) {
			return false;
		}
		return true;
	}

	public java.util.Date getExpiredTime(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}

	public Long getIdFromToken(String token) {
		final Claims claims = getAllClaimsFromToken(token);
		return Long.valueOf((Integer)claims.getOrDefault("id", 0L));
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}
}
