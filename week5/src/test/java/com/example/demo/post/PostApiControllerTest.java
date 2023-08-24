package com.example.demo.post;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setMockMvc() {
        postRepository.deleteAll();

        User user = userRepository.save(User.builder()
                .email("email@email.com")
                .password("password")
                .nickname("nickname")
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @DisplayName("[POST /api/v1/users/{user-id}/posts] addPost: 포스팅 추가에 성공")
    @Test
    void addPost() throws Exception {
        // Given
        final String url = "/api/v1/users/1/posts";
        final String content = "content";
        final PostAddRequestDto requestDto = PostAddRequestDto.builder()
                .content(content)
                .build();

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("nickname");

        // When
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .principal(principal)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print());


        // Then
        result.andExpect(status().isCreated());

        List<Post> posts = postRepository.findAll();

        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("[GET /api/v1/users/{user-id}/posts] findAllPosts: 포스팅 목록 조회에 성공")
    @Test
    void findAllPosts() throws Exception {
        // Given
        final String url = "/api/v1/users/1/posts";
        Post savedPost = createDefaultPost();

        // When
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(savedPost.getContent()));
    }

    @DisplayName("[GET /api/v1/users/{user-id}/posts/{post-id}] findPost: 포스팅 조회에 성공")
    @Test
    void findPost() throws Exception {
        // Given
        final String url = "/api/v1/users/1/posts/{post-id}";
        Post savedPost = createDefaultPost();


        // When
        final ResultActions result = mockMvc.perform(get(url, savedPost.getId()))
                .andDo(print());

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(savedPost.getContent()));
    }

    @DisplayName("[DELETE /api/v1/users/{user-id}/posts/{post-id] deletePost: 포스팅 삭제에 성공")
    @Test
    void deletePost() throws Exception {
        // Given
        final String url = "/api/v1/users/1/posts/{post-id}";
        Post savedPost = createDefaultPost();

        // When
        ResultActions result = mockMvc.perform(delete(url, savedPost.getId()))
                .andDo(print());

        // Then
        result.andExpect(status().isOk());

        List<Post> posts = postRepository.findAll();
        assertThat(posts).isEmpty();
    }

    @DisplayName("[PUT /api/v1/users/{user-id}/posts/{post-id] updatePost: 포스팅 수정에 성공")
    @Test
    void updatePost() throws Exception {
        // Given
        final String url = "/api/v1/users/1/posts/{post-id}";
        Post savedPost = createDefaultPost();

        // When
        final String newContent = "new content";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .content(newContent)
                .build();

        ResultActions result = mockMvc.perform(put(url, savedPost.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print());

        // Then
        result.andExpect(status().isOk());

        Post post = postRepository.findById(savedPost.getId()).get();

        assertThat(post.getContent()).isEqualTo(newContent);
    }

    private Post createDefaultPost() {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();

        return postRepository.save(Post.builder()
                .content("content")
                .location("location")
                .user(user)
                .build());
    }
}