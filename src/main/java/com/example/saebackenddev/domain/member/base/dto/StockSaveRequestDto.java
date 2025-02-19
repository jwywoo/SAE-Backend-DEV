package com.example.saebackenddev.domain.member.base.dto;

import lombok.Getter;

@Getter
public class StockSaveRequestDto {
    private final String username;

    public StockSaveRequestDto(String username) {
        this.username = username;
    }
}
