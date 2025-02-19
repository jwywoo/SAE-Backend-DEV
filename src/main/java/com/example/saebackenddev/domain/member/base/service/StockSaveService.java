package com.example.saebackenddev.domain.member.base.service;

import com.example.saebackenddev.domain.member.auth.entity.MemberEntity;
import com.example.saebackenddev.domain.member.auth.repository.MemberRepository;
import com.example.saebackenddev.domain.member.base.dto.SavedStockResponseDto;
import com.example.saebackenddev.domain.member.base.entity.SavedStockEntity;
import com.example.saebackenddev.domain.member.base.repository.SavedStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockSaveService {
    final MemberRepository memberRepository;
    final SavedStockRepository savedStockRepository;

    public ResponseEntity<String> stockSave(MemberEntity memberEntity, String tickerCode){
        SavedStockEntity savedStockEntity = new SavedStockEntity(memberEntity, tickerCode);
        savedStockRepository.save(savedStockEntity);
        return ResponseEntity.status(HttpStatus.OK).body("Saved!");
    }

    public ResponseEntity<List<SavedStockResponseDto>> stockGet(MemberEntity memberEntity) {
        List<SavedStockEntity> stockEntities = savedStockRepository.findAllByRequestedMember(memberEntity);
        List<SavedStockResponseDto> savedStockResponseDtoList = stockEntities.stream()
                .map(entity -> new SavedStockResponseDto(entity.getTickerCode()))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(savedStockResponseDtoList);
    }
}
