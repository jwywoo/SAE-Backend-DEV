package com.example.saebackenddev.domain.member.auth.service;

import com.example.saebackenddev.domain.member.auth.dto.LoginResponseDto;
import com.example.saebackenddev.domain.member.auth.entity.MemberEntity;
import com.example.saebackenddev.domain.member.auth.entity.MemberRoleEnum;
import com.example.saebackenddev.domain.member.auth.repository.MemberRepository;
import com.example.saebackenddev.global.jwt.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Value("${google.token.uri}")
    private String tokenUri;

    @Value("${google.userinfo.uri}")
    private String userInfoUri;

    public LoginResponseDto googleLogin(String code, HttpServletResponse response) {
        // Step 1: Exchange code for access token
        String accessToken = getAccessToken(code);

        // Step 2: Get user info using the access token
        JsonNode userInfo = getUserInfo(accessToken);
        String email = userInfo.get("email").asText();
        String googleId = userInfo.get("sub").asText();  // Google ID
        String username = email.split("@")[0]; // Extract username from email

        // Step 3: Find or Register User
        MemberEntity user = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    // Create new user if not exists
                    MemberEntity newUser = MemberEntity.builder()
                            .username(username)
                            .email(email)
                            .googleId(googleId)
                            .role(MemberRoleEnum.USER)
                            .build();
                    return memberRepository.save(newUser);
                });

        // Step 4: Generate JWT token
        String jwtToken = jwtUtil.createToken(user.getUsername());
        response.addHeader("Authorization",  jwtToken);

        return new LoginResponseDto(user.getId(),username);
    }

    private String getAccessToken(String code) {
        URI uri = UriComponentsBuilder.fromUriString(tokenUri)
                .queryParam("code", code)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("grant_type", "authorization_code")
                .build().toUri();

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(uri, null, JsonNode.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().get("access_token").asText();
        } else {
            throw new RuntimeException("Failed to retrieve access token from Google");
        }
    }

    private JsonNode getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, JsonNode.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve user info from Google");
        }
    }
}
