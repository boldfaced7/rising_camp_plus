package com.example.demo.Post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto save(PostAddRequestDto requestDto) {
        Post post = postRepository.save(requestDto.toEntity());
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAll() {
        return postRepository.findAll().stream().map(PostListResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + id));

        return new PostResponseDto(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + id));
        postRepository.delete(post);
    }

    @Transactional
    public PostResponseDto update(Long id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + id));

        post.update(requestDto.getContent());

        return new PostResponseDto(post);
    }
}
