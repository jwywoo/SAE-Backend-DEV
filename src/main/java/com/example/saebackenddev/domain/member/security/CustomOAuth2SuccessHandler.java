package com.example.saebackenddev.domain.member.security;

import com.example.saebackenddev.domain.member.jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j(topic = "OAuth2 로그인 및 JWT 생성")
@RequiredArgsConstructor
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");  // Extract user email

        // Generate JWT Tokens
        String accessToken = jwtUtil.createToken(email, com.example.saebackenddev.domain.member.entity.Role.USER);
        String refreshToken = jwtUtil.createRefreshToken(email, com.example.saebackenddev.domain.member.entity.Role.USER);

        // Set Response Headers (Like in `JwtAuthenticationFilter`)
        response.addHeader("Authorization", accessToken);
        response.addHeader(JwtUtil.REFRESH_HEADER, refreshToken);
        response.setStatus(HttpServletResponse.SC_OK);

        log.info("Generated Access Token: {}", accessToken);
        log.info("Generated Refresh Token: {}", refreshToken);

        // Return Response Body
        writeResponse(response, "OAuth2 Login Successful");
    }

    private void writeResponse(HttpServletResponse response, String message) {
        try {
            response.setContentType("text/plain");
            PrintWriter writer = response.getWriter();
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            log.error("Response writing error: {}", e.getMessage());
        }
    }
}
