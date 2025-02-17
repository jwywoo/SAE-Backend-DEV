package com.example.saebackenddev.domain.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = true)  // Allow null for OAuth users
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Column(nullable = true)
    private String oauthProvider; // e.g., "google"

    public MemberEntity(String username, String password, String email, Role role, String oauthProvider) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.oauthProvider = oauthProvider;
    }

    public MemberEntity update(String username, String email) {
        this.username = username;
        this.email = email;
        return this;
    }
}
