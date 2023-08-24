package com.example.demo.subscribe;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/users/{user-id}/subscribes")
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    @PostMapping
    public ResponseEntity<Void> follow(@PathVariable(name = "user-id") Long userId) {
        subscribeService.follow(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unfollow(@PathVariable(name = "user-id") Long userId) {
        subscribeService.unfollow(userId);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/followers")
    public ResponseEntity<List<SubscribeFollowerResponseDto>> findFollowers(@PathVariable(name = "user-id") Long userId) {
        return ResponseEntity.ok()
                .body(subscribeService.followers(userId));
    }

    @GetMapping("followings")
    public ResponseEntity<List<SubscribeFollowingResponseDto>> findFollowings(@PathVariable(name = "user-id") Long userId) {
        return ResponseEntity.ok()
                .body(subscribeService.followings(userId));
    }
}
