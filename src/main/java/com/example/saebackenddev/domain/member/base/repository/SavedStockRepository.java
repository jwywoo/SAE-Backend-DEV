package com.example.saebackenddev.domain.member.base.repository;

import com.example.saebackenddev.domain.member.auth.entity.MemberEntity;
import com.example.saebackenddev.domain.member.base.entity.SavedStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedStockRepository extends JpaRepository<SavedStockEntity, Long> {
    List<SavedStockEntity> findAllByRequestedMember(MemberEntity memberEntity);
}
