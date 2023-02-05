package com.website.backendtwo.utility;

import com.website.backendtwo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class JwtHelper {
    private static final String SECRET_KEY = "4428472B4B6250655368566D5971337436763979244226452948404D63516654";

    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String encode(User user) {
        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .signWith(getSignInKey())
                .setIssuedAt(Date.from(Instant.now()))
                .compact();
    }

    public static User decode(String token) {
        String userId = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        User user = new User();
        user.setUserId(Integer.parseInt(userId));
        return user;
    }

    public static Boolean validate(String token, User user) {
        User tokenUser = decode(token);
        return Objects.equals(user.getUserId(), tokenUser.getUserId());
    }
}
