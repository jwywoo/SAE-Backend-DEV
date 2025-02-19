package com.example.saebackenddev.domain.member.auth.controller;

import com.example.saebackenddev.domain.member.auth.dto.LoginResponseDto;
import com.example.saebackenddev.domain.member.auth.service.GoogleOAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberAuthController {

    private final GoogleOAuthService googleOAuthService;

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.redirect.uri}")
    private String googleRedirectUri;

    @GetMapping("/google/login")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        String googleLoginUrl = "https://accounts.google.com/o/oauth2/auth"
                + "?client_id=" + googleClientId
                + "&redirect_uri=" + googleRedirectUri
                + "&response_type=code"
                + "&scope=email%20profile";

        response.sendRedirect(googleLoginUrl);
    }

    @GetMapping("/google/callback")
    public LoginResponseDto googleLogin(@RequestParam String code, HttpServletResponse response) {
        return googleOAuthService.googleLogin(code, response);
    }
}
