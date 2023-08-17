package com.example.demo.Post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class PostApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext context;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        postRepository.deleteAll();
    }

    @DisplayName("addPost: 포스팅 추가에 성공")
    @Test
    void addPost() throws Exception {
        // Given
        final String url = "/api/v1/posts";
        final String content = "content";
        final PostAddRequestDto requestDto = PostAddRequestDto.builder()
                .content(content)
                .build();

        // When
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)));


        // Then
        result.andExpect(status().isCreated());

        List<Post> posts = postRepository.findAll();

        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllPosts: 포스팅 목록 조회에 성공")
    @Test
    void findAllPosts() throws Exception {
        // Given
        final String url = "/api/v1/posts";
        final String content = "content";

        postRepository.save(Post.builder()
                .content(content)
                .build());

        // When
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("findPost: 포스팅 조회에 성공")
    @Test
    void findPost() throws Exception {
        // Given
        final String url = "/api/v1/posts/{id}";
        final String content = "content";

        Post savedPost = postRepository.save(Post.builder()
                .content(content)
                .build());

        // When
        final ResultActions result = mockMvc.perform(get(url, savedPost.getId()));

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content));
    }

    @DisplayName("deletePost: 포스팅 삭제에 성공")
    @Test
    void deletePost() throws Exception {
        // Given
        final String url = "/api/v1/posts/{id}";
        final String content = "content";

        Post savedPost = postRepository.save(Post.builder()
                .content(content)
                .build());

        // When
        ResultActions result = mockMvc.perform(delete(url, savedPost.getId()));

        // Then
        result.andExpect(status().isOk());

        List<Post> posts = postRepository.findAll();
        assertThat(posts).isEmpty();
    }

    @DisplayName("updatePost: 포스팅 수정에 성공")
    @Test
    void updatePost() throws Exception {
        // Given
        final String url = "/api/v1/posts/{id}";
        final String content = "content";

        Post savedPost = postRepository.save(Post.builder()
                .content(content)
                .build());

        // When
        final String newContent = "new content";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .content(newContent)
                .build();

        ResultActions result = mockMvc.perform(put(url, savedPost.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)));

        // Then
        result.andExpect(status().isOk());

        Post post = postRepository.findById(savedPost.getId()).get();

        assertThat(post.getContent()).isEqualTo(newContent);
    }
}