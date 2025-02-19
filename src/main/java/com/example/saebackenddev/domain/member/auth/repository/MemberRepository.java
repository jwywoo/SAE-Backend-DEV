package com.example.saebackenddev.domain.member.auth.repository;

import com.example.saebackenddev.domain.member.auth.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUsername(String username);
    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByGoogleId(String googleId);
}
