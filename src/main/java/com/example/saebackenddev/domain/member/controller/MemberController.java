package com.example.saebackenddev.domain.member.controller;

import com.example.saebackenddev.domain.member.dto.SignupRequestDto;
import com.example.saebackenddev.domain.member.dto.SignupResponseDto;
import com.example.saebackenddev.domain.member.jwt.JwtUtil;
import com.example.saebackenddev.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User API", description = "User management operations")

public class MemberController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    @Operation(summary = "User Signup", description = "Register a new user with username, password, and email.")

    public ResponseEntity<SignupResponseDto> signup(
            @Valid @RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto response = memberService.registerMember(signupRequestDto);
        return ResponseEntity.ok(response);
    }
}
