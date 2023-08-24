package com.example.demo.comment;

import com.example.demo.post.Post;
import com.example.demo.post.PostRepository;
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
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@SpringBootTest
@AutoConfigureMockMvc
class CommentApiControllerTest {
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

    @Autowired
    CommentRepository commentRepository;

    User defaultUser;
    Post defaultPost;
    Long userId = 1L;
    Long postId = 1L;
    @BeforeEach
    void setMockMvc() {
        commentRepository.deleteAll();

        defaultUser = userRepository.save(User.builder()
                .email("email@email.com")
                .password("password")
                .nickname("nickname")
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(defaultUser, defaultUser.getPassword(), defaultUser.getAuthorities()));

        Post defaultPost = postRepository.save(Post.builder()
                .content("content")
                .location("location")
                .user(defaultUser)
                .build());
    }
    private Comment createDefaultComment() {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();

        return commentRepository.save(Comment.builder()
                .content("content")
                .user(defaultUser)
                .post(defaultPost)
                .build());
    }

    @DisplayName("[Post /api/v1/users/{user-id}/posts/{post-id}/comments] addComment: 댓글 추가에 성공")
    @Test
    void addComment() throws Exception {
        // Given
        String url = "/api/v1/users/{user-id}/posts/{post-id}/comments";
        String content = "content";
        CommentAddRequestDto requestDto = CommentAddRequestDto.builder()
                .content(content).build();

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("nickname");

        // When
        ResultActions resultActions = mockMvc.perform(post(url, userId, postId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isCreated());

        List<Comment> comments = commentRepository.findAll();

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("[DELETE /api/v1/users/{user-id}/posts/{post-id}/comments/{comment-id] deleteComment: 댓글 삭제에 성공 ")
    @Test
    void deleteComment() throws Exception {
        // Given
        String url = "/api/v1/users/{user-id}/posts/{post-id}/comments/{comment-id}";
        Comment savedComment = createDefaultComment();


        // When
        ResultActions result = mockMvc.perform(delete(url,userId, postId, savedComment.getId()))
                .andDo(print());

        // Then
        result.andExpect(status().isOk());

        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).isEmpty();
    }

    @DisplayName("[GET /api/v1/users/{user-id}/posts/{post-id}/comments] findComments: 댓글 전체 조회에 성공")
    @Test
    void findComments() throws Exception{
        // Given
        String url = "/api/v1/users/{user-id}/posts/{post-id}/comments";
        Comment savedComment1 = createDefaultComment();
        Comment savedComment2 = createDefaultComment();
        Comment savedComment3 = createDefaultComment();

        // When
        final ResultActions result = mockMvc.perform(get(url, userId, postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        result
                .andExpect(status().isOk());
    }
}