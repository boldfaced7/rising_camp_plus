package com.example.demo.refreshtoken;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/api/v1/token")
    public ResponseEntity<AccessTokenCreateResponseDto> createNewAccessToken
            (@RequestBody AccessTokenCreateRequestDto requestDto) {

        String newAccessToken = tokenService.createNewAccessToken(requestDto.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AccessTokenCreateResponseDto.builder()
                        .accessToken(newAccessToken).build());
    }
}
