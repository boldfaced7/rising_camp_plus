package com.example.demo.post;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto save(Long userId, PostAddRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + userId));

        Post post = postRepository.save(requestDto.toEntity(user));
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
        authorizePostUser(post);
        postRepository.delete(post);

    }

    @Transactional
    public PostResponseDto update(Long id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + id));

        authorizePostUser(post);
        post.update(requestDto.getContent());

        return new PostResponseDto(post);
    }

    private static void authorizePostUser(Post post) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (post.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("Not authorized");
        }
    }
}
