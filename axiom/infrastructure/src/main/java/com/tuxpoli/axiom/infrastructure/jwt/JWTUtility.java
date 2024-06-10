package com.tuxpoli.axiom.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JWTUtility {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String issueToken(String id, String subject) {
        return issueToken(id, subject, Map.of());
    }

    public String issueToken(String id, String subject, List<String> scopes) {
        return issueToken(id, subject, Map.of("scopes", scopes));
    }

    public String issueToken(String id, String subject, String ...scopes) {
        return issueToken(id, subject, Map.of("scopes", scopes));
    }

    public String issueToken(String id, String subject, Map<String, Object> claims) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(subject)
                .id(id)
                .issuer("tuxpoli")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)))
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String getId(String token) {
        return getPayload(token).getId();
    }

    public String getSubject(String token) {
        return getPayload(token).getSubject();
    }

    public Object getClaim(String token, String claim) {
        return getPayload(token).get(claim);
    }

    private Claims getPayload(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return getPayload(token).getExpiration().before(Date.from(Instant.now()));
    }
}
