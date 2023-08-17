package com.example.demo.Post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostApiController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> addPost(@RequestBody PostAddRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.save(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<PostListResponseDto>> findAllPosts() {
        List<PostListResponseDto> posts = postService.findAll();
        return ResponseEntity.ok()
                .body(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findPost(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(postService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
                                                      @RequestBody PostUpdateRequestDto requestDto) {
        return ResponseEntity.ok()
                .body(postService.update(id, requestDto));
    }
}
