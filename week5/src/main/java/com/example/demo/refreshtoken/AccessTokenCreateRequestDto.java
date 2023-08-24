package com.example.demo.refreshtoken;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccessTokenCreateRequestDto {
    private String refreshToken;

    @Builder
    public AccessTokenCreateRequestDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
