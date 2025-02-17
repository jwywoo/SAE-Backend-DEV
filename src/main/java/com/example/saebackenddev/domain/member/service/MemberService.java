package com.example.saebackenddev.domain.member.service;

import com.example.saebackenddev.domain.member.dto.SignupRequestDto;
import com.example.saebackenddev.domain.member.dto.SignupResponseDto;
import com.example.saebackenddev.domain.member.entity.MemberEntity;
import com.example.saebackenddev.domain.member.entity.Role;
import com.example.saebackenddev.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponseDto registerMember(SignupRequestDto requestDto) {
        if (memberRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        if (requestDto.getPassword() == null || requestDto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        MemberEntity newUser = MemberEntity.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .role(Role.USER)
                .build();

        memberRepository.save(newUser);

        return new SignupResponseDto(newUser.getUsername(), newUser.getEmail(), newUser.getRole());
    }
}
