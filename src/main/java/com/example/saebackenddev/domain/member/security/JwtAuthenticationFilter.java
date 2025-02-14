package com.example.saebackenddev.domain.member.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.saebackenddev.domain.member.dto.LoginRequestDto;
import com.example.saebackenddev.domain.member.entity.Role;
import com.example.saebackenddev.domain.member.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error("Login request error: {}", e.getMessage());
            throw new RuntimeException("Invalid login request");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        Set<Role> roles = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRoles();
        Role role = roles.iterator().next(); // Get the first role

        String token = jwtUtil.createToken(username, role);
        response.addHeader("Authorization", token);
        String refreshToken = jwtUtil.createRefreshToken(username, role);
        response.addHeader(JwtUtil.REFRESH_HEADER, refreshToken);
        response.setStatus(HttpServletResponse.SC_OK);
        writeResponse(response, "Login successful");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        writeResponse(response, "Invalid username or password");
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

