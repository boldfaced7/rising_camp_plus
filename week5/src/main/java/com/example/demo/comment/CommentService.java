package com.example.demo.comment;

import com.example.demo.post.Post;
import com.example.demo.post.PostRepository;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponseDto save(Long id, CommentAddRequestDto requestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + id));

        Comment comment = commentRepository.save(requestDto.toEntity(user, post));
        return new CommentResponseDto(comment);
    }

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + id));

        commentRepository.delete(comment);
    }

    public List<CommentResponseDto> comments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + postId));

        return commentRepository.findAllByPost(post).stream()
                .map(comment -> new CommentResponseDto(comment))
                .toList();
    }
}
