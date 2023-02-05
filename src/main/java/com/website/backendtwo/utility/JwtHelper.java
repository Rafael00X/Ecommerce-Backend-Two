package com.website.backendtwo.utility;

import com.website.backendtwo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

public class JwtHelper {
    private static final String SECRET_KEY = "4428472B4B6250655368566D5971337436763979244226452948404D63516654";
    private static final Integer VALID_DURATION_IN_SECONDS = 7 * (24 * 60 * 60);

    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String encode(User user) {
        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .claim("email", user.getEmail())
                .claim("userName", user.getUserName())
                .signWith(getSignInKey())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(VALID_DURATION_IN_SECONDS)))
                .compact();
    }

    public static User decode(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        Integer userId = Integer.parseInt(claims.getSubject());
        String userName = claims.get("userName", String.class);
        String email = claims.get("email", String.class);
        return new User(userId, userName, null, email, token);
    }
}
