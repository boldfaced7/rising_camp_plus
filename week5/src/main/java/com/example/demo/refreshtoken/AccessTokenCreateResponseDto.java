package com.example.demo.refreshtoken;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccessTokenCreateResponseDto {
    private String accessToken;

    @Builder
    public AccessTokenCreateResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
