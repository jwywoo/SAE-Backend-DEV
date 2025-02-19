package com.example.saebackenddev.domain.member.base.service;

import com.example.saebackenddev.domain.member.auth.entity.MemberEntity;
import com.example.saebackenddev.domain.member.auth.repository.MemberRepository;
import com.example.saebackenddev.domain.member.base.entity.SavedStockEntity;
import com.example.saebackenddev.domain.member.base.repository.SavedStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockSaveService {
    final MemberRepository memberRepository;
    final SavedStockRepository savedStockRepository;

    public ResponseEntity<?> stockSave(MemberEntity memberEntity, String tickerCode){
        return null;
    }
}
