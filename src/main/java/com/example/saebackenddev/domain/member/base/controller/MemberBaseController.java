package com.example.saebackenddev.domain.member.base.controller;

import com.example.saebackenddev.domain.member.base.dto.SavedStockResponseDto;
import com.example.saebackenddev.domain.member.base.service.StockSaveService;
import com.example.saebackenddev.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/base")
public class MemberBaseController {
    private final StockSaveService stockSaveService;

    @PostMapping("/stock/{tickerCode}")
    public ResponseEntity<String> stockSave(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String tickerCode){

        return stockSaveService.stockSave(userDetails.getMember(), tickerCode);
    }

    @GetMapping("/stock")
    public ResponseEntity<List<SavedStockResponseDto>> stockGet(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return stockSaveService.stockGet(userDetails.getMember());
    }
}
