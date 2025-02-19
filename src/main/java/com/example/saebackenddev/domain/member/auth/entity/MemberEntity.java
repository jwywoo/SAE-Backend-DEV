package com.example.saebackenddev.domain.member.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String googleId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

    public MemberEntity(String googleId, String email, MemberRoleEnum role) {
        this.googleId = googleId;
        this.email = email;
        this.username = parseUsernameFromEmail(email); // Extract username from email
        this.role = role != null ? role : MemberRoleEnum.USER;
    }

    private String parseUsernameFromEmail(String email) {
        return email.substring(0, email.indexOf('@')); // Extract part before '@'
    }
}
