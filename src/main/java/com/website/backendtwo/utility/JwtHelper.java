package com.website.backendtwo.utility;

import com.website.backendtwo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class JwtHelper {
    private static final String SECRET_KEY = "secret-key";

    public static String encode(User user) {
        return Jwts.builder()
                .claim("email", user.getEmail())
                .claim("password", user.getPassword())
                .claim("userId", user.getUserId())
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static Jws<Claims> decode(String jwt) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt);
    }

    public static Boolean validate(String jwt, Integer userId) {
        Jws<Claims> result = decode(jwt);
        return Objects.equals(userId, result.getBody().get("userId", Integer.class));
    }
}
