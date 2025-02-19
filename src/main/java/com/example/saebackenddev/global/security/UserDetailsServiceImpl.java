package com.example.saebackenddev.global.security;

import com.example.saebackenddev.domain.member.auth.entity.MemberEntity;
import com.example.saebackenddev.domain.member.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Loading user by username: " + username);
        Optional<MemberEntity> member = memberRepository.findByUsername(username);
        return member.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public UserDetails loadUserByGoogleId(String googleId) throws UsernameNotFoundException {
        Optional<MemberEntity> member = memberRepository.findByGoogleId(googleId);
        return member.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with Google ID: " + googleId));
    }
}

