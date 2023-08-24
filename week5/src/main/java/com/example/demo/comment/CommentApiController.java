package com.example.demo.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/users/{user-id}/posts/{post-id}/comments")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable(name = "post-id") Long postId,
                                                         @RequestBody CommentAddRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.save(postId, requestDto));
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "comment-id") Long id) {
        commentService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findComments(@PathVariable(name = "post-id") Long postId) {
        return ResponseEntity.ok()
                .body(commentService.comments(postId));
    }
}
