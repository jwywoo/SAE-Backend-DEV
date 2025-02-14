package com.example.saebackenddev.domain.member.dto;

import com.example.saebackenddev.domain.member.entity.Role;
import lombok.Getter;

import java.util.Set;

@Getter
public class SignupResponseDto {
    private final String username;
    private final String email;
    private final Set<Role> roles;

    public SignupResponseDto(String username, String email, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}