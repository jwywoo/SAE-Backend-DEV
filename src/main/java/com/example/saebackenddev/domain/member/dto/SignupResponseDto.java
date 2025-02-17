package com.example.saebackenddev.domain.member.dto;

import com.example.saebackenddev.domain.member.entity.Role;
import lombok.Getter;

import java.util.Set;

@Getter
public class SignupResponseDto {
    private final String username;
    private final String email;
    private final Role role;

    public SignupResponseDto(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }
}