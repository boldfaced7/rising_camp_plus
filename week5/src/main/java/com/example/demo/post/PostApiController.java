package com.example.demo.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/{user-id}/posts")
public class PostApiController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> addPost(@PathVariable(name = "user-id") Long userId,
                                                   @RequestBody PostAddRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.save(userId, requestDto));
    }

    @GetMapping
    public ResponseEntity<List<PostListResponseDto>> findAllPosts() {
        List<PostListResponseDto> posts = postService.findAll();
        return ResponseEntity.ok()
                .body(posts);
    }

    @GetMapping("/{post-id}")
    public ResponseEntity<PostResponseDto> findPost(@PathVariable(name = "post-id") Long id) {
        return ResponseEntity.ok()
                .body(postService.findById(id));
    }

    @DeleteMapping("/{post-id}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "post-id") Long id) {
        postService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/{post-id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable(name = "post-id") Long id,
                                                      @RequestBody PostUpdateRequestDto requestDto) {
        return ResponseEntity.ok()
                .body(postService.update(id, requestDto));
    }
}
