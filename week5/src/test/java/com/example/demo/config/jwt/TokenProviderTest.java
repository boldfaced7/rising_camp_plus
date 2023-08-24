package com.example.demo.config.jwt;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken: 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있음")
    @Test
    void generateToken() {
        // Given
        User testUser = userRepository.save(User.builder()
                .nickname("nickname").password("password").name("name")
                .website("website").bio("bio").email("email")
                .phone("phone").gender("gender").build());

        // When
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // Then
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validToken: 유효한 토큰인 경우 유효성 검증에 성공")
    @Test
    void validToken() {
        // Given
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);

        // When
        boolean result = tokenProvider.validToken(token);

        // Then
        assertThat(result).isTrue();
    }

    @DisplayName("validToken: 만료된 토큰인 경우 유효성 검증에 실패")
    @Test
    void validToken_invalidToken() {
        // Given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        // When
        boolean result = tokenProvider.validToken(token);

        // Then
        assertThat(result).isFalse();
    }

    @DisplayName("getAuthentication: 토큰 기반으로 인증 정보를 가져올 수 있음")
    @Test
    void getAuthentication() {
        // Given
        String userEmail = "user@user.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        // When
        Authentication authentication = tokenProvider.getAuthentication(token);

        // Then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    @DisplayName("getUserId: 토큰으로 유저 ID를 가져올 수 있음")
    @Test
    void getUserId() {
        // Given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        // When
        Long userIdByToken = tokenProvider.getUserId(token);

        // Then
        assertThat(userIdByToken).isEqualTo(userId);
    }
}