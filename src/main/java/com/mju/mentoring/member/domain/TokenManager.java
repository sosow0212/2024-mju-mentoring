package com.mju.mentoring.member.domain;

import com.mju.mentoring.member.exception.ExpiredTokenException;
import com.mju.mentoring.member.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    private static final String ID_CLAIM_NAME = "id";

    @Value("${auth.jwt.secret-key}")
    private String secretKey;

    @Value("${auth.jwt.expired-second}")
    private Long expiredSecond;

    private Key key;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createAccessToken(final Long id) {
        Claims claim = Jwts.claims();

        claim.put(ID_CLAIM_NAME, id);

        Date now = new Date();
        Date expiredIn = new Date(now.getTime() + expiredSecond);

        return Jwts.builder()
            .setIssuedAt(now)
            .setClaims(claim)
            .setExpiration(expiredIn)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public Long parseToken(final String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(ID_CLAIM_NAME, Long.class);
        } catch (ExpiredJwtException exception) {
            throw new ExpiredTokenException();
        } catch (MalformedJwtException | UnsupportedJwtException | SignatureException exception) {
            throw new InvalidTokenException();
        }
    }
}
