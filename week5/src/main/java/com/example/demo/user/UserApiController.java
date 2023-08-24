package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserAddRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(userService.findById(id));
    }
    @GetMapping
    public ResponseEntity<UserResponseDto> findUserByNickname(@RequestParam String nickname) {
        return ResponseEntity.ok()
                .body(userService.findByNickname(nickname));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                      @RequestBody UserUpdateRequestDto requestDto) {
        return ResponseEntity.ok()
                .body(userService.update(id, requestDto));
    }
}
