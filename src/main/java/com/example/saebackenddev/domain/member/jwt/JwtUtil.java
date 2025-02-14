package com.example.saebackenddev.domain.member.jwt;

import com.example.saebackenddev.domain.member.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@RequiredArgsConstructor
@Component
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Authorization_Refresh";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    // ✅ Create Access Token
    public String createToken(String username, Role role) {
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role.name()) // Store role as a claim
                        .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                        .setIssuedAt(new Date())
                        .signWith(key)
                        .compact();
    }

    // ✅ Create Refresh Token
    public String createRefreshToken(String username, Role role) {
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role.name())
                        .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                        .setIssuedAt(new Date())
                        .signWith(key)
                        .compact();
    }

    // ✅ Extract Token from HTTP Header
    public String getJwtFromHeader(HttpServletRequest request, String headerName) {
        String bearerToken = request.getHeader(headerName);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // ✅ Validate JWT Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims are empty.");
        }
        return false;
    }

    // ✅ Extract User Info from Token
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // ✅ Clean Token (Remove "Bearer " Prefix)
    public String cleanToken(String token) {
        if (token == null) {
            return null;
        }
        token = token.trim();
        if (token.startsWith(BEARER_PREFIX)) {
            token = token.substring(7).trim();
        }
        return token;
    }
}
