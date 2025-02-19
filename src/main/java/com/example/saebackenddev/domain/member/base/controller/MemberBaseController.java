package com.example.saebackenddev.domain.member.base.controller;

import com.example.saebackenddev.domain.member.base.service.StockSaveService;
import com.example.saebackenddev.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/base")
public class MemberBaseController {
    private final StockSaveService stockSaveService;

    @PostMapping("/save/{tickerCode}")
    public ResponseEntity<?> stockSave(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String tickerCode){
        return stockSaveService.stockSave(userDetails.getMember(), tickerCode);
    }
}
