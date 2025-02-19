package com.example.saebackenddev.domain.member.auth.service;

import com.example.saebackenddev.domain.member.auth.dto.LoginResponseDto;
import com.example.saebackenddev.domain.member.auth.entity.MemberEntity;
import com.example.saebackenddev.domain.member.auth.repository.MemberRepository;
import com.example.saebackenddev.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberAuthService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public LoginResponseDto googleLogin(OAuth2AuthenticationToken authentication, HttpServletResponse response) {
        // Extract Google user info
        String googleId = authentication.getPrincipal().getAttribute("sub"); // Unique Google ID
        String email = authentication.getPrincipal().getAttribute("email"); // User email

        // Check if user exists in database
        Optional<MemberEntity> existingUser = memberRepository.findByGoogleId(googleId);

        MemberEntity member;
        if (existingUser.isPresent()) {
            member = existingUser.get();
        } else {
            // Register new user
            member = MemberEntity.builder()
                    .googleId(googleId)
                    .email(email)
                    .build();
            memberRepository.save(member);
        }

        // Generate JWT token
        String token = jwtUtil.createToken(member.getUsername());

        // Store JWT in response header
        response.addHeader("Authorization", "Bearer " + token);

        // Return user information
        return new LoginResponseDto(member.getId(), member.getUsername());
    }
}
