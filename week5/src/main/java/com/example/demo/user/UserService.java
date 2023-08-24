package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto save(UserAddRequestDto requestDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = userRepository.save(requestDto.toEntity(
                encoder.encode(requestDto.getPassword())));

        return new UserResponseDto(user);
    }

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + id));

        return new UserResponseDto(user);
    }

    // TokenService용
    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + userId));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }


    public UserResponseDto findByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + nickname));

        return new UserResponseDto(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + id));

        userRepository.delete(user);
    }

    @Transactional
    public UserResponseDto update(Long id, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + id));

        user.update(requestDto.getNickname(), requestDto.getName(),
                requestDto.getWebsite(), requestDto.getBio(), requestDto.getGender());

        return new UserResponseDto(user);
    }

}
