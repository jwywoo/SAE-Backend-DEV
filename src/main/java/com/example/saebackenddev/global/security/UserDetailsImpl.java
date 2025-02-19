package com.example.saebackenddev.global.security;

import com.example.saebackenddev.domain.member.auth.entity.MemberEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String googleId;
    private final String email;

    public UserDetailsImpl(MemberEntity member) {
        this.username = member.getUsername();
        this.googleId = member.getGoogleId();
        this.email = member.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // No roles
    }

    @Override
    public String getPassword() {
        return null; // No password needed
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

