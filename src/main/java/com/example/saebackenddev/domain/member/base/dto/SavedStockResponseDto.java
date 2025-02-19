package com.example.saebackenddev.domain.member.base.dto;

import lombok.Getter;

@Getter
public class SavedStockResponseDto {
    private final String tickerCode;

    public SavedStockResponseDto(String tickerCode) {
        this.tickerCode=tickerCode;
    }
}
