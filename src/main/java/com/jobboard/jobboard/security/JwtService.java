package com.jobboard.jobboard.security;

import com.jobboard.jobboard.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET = "my-super-secure-key-12345678901234567890";
    private static final long EXPIRATION = 3600000; // 1 hour in milliseconds

    private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String jwt) {
        var parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
        var claimsJws = parser.parseClaimsJws(jwt);
        String email = claimsJws.getBody().getSubject();
        return email;
    }

    public boolean validateToken(String jwt, UserDetails userDetails) {
        var parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
        var claims = parser.parseClaimsJws(jwt).getBody();
        Date exipry = claims.getExpiration();
        boolean notExpired = exipry.after(new Date());
        boolean emailMatched = claims.getSubject().equals(userDetails.getUsername());
        return notExpired && emailMatched;
    }
}
