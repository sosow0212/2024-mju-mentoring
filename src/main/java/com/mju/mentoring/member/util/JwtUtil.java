package com.mju.mentoring.member.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "vatgbnwpphfplurslgolidijjspfrbvatgbnwpphfplurslgolidijjspfrb";
    private static final int BEARER_LENGTH = 7;
    private static final int JWT_EXPIRE_SECONDS = 3600;

    public static String generateToken(final String username) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        Date now = new Date();
        Date expiration = new Date(now.getTime() + JWT_EXPIRE_SECONDS * 1000);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public static String extractUsername(final String token) {
        String removeBearerWordToken = token.substring(BEARER_LENGTH);
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        validateTokenWithKey(removeBearerWordToken, key);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(removeBearerWordToken)
                .getBody();

        return claims.getSubject();
    }

    private static void validateTokenWithKey(final String token, final SecretKey key) {
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
