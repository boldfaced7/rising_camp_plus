package com.example.demo.subscribe;


import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SubscribeApiControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext context;

    @Autowired
    SubscribeRepository subscribeRepository;

    @Autowired
    UserRepository userRepository;

    User defaultUser1;
    User defaultUser2;

    Long userId = 1L;

    @BeforeEach
    void setMockMvc() {
        userRepository.deleteAll();
        subscribeRepository.deleteAll();

        defaultUser1 = userRepository.save(User.builder()
                .email("email1@email.com")
                .password("password1")
                .nickname("nickname1")
                .build());

        defaultUser2 = userRepository.save(User.builder()
                .email("email2@email.com")
                .password("password2")
                .nickname("nickname2")
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(defaultUser2, defaultUser2.getPassword(), defaultUser2.getAuthorities()));
    }

    @Test
    void follow() throws Exception {
        // Given
        String url = "/api/v1/users/{user-id}/subscribes";

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("email2@email.com");

        // When
        ResultActions resultActions = mockMvc.perform(post(url, userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .principal(principal))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isCreated());

        List<Subscribe> subscribes = subscribeRepository.findAll();
        assertThat(subscribes.size()).isEqualTo(1);
        assertThat(subscribes.get(0).getFollowedUser().getEmail()).isEqualTo("email1@email.com");
        assertThat(subscribes.get(0).getFollowingUser().getEmail()).isEqualTo("email2@email.com");

    }

    @Test
    void unfollow() throws Exception {
        // Given
        createDefaultSubscribe();
        String url = "/api/v1/users/{user-id}/subscribes";

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("email2@email.com");

        // When
        ResultActions resultActions = mockMvc.perform(delete(url, userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .principal(principal))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());

        List<Subscribe> subscribes = subscribeRepository.findAll();
        assertThat(subscribes.get(0).getFollowedUser().getEmail()).isEqualTo("email2@email.com");
        assertThat(subscribes.get(0).getFollowingUser().getEmail()).isEqualTo("email1@email.com");
        assertThat(subscribes.get(1).getFollowedUser().getEmail()).isEqualTo("email1@email.com");
        assertThat(subscribes.get(1).getFollowingUser().getEmail()).isEqualTo("email2@email.com");
        //assertThat(subscribes.size()).isEqualTo(1);

    }

    @Test
    void findFollowers() throws Exception {
        // Given
        createDefaultSubscribe();
        String url = "/api/v1/users/{user-id}/subscribes/followers";

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("email2@email.com");

        // When
        ResultActions resultActions = mockMvc.perform(get(url, userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .principal(principal))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void findFollowings() throws Exception {
        // Given
        createDefaultSubscribe();
        String url = "/api/v1/users/{user-id}/subscribes/followings";

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("email2@email.com");

        // When
        ResultActions resultActions = mockMvc.perform(get(url, userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .principal(principal))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    private void createDefaultSubscribe() {
        subscribeRepository.save(Subscribe.builder() // 1L
                .followingUser(defaultUser1)
                .followedUser(defaultUser2)
                .build());

        subscribeRepository.save(Subscribe.builder() // 2L
                .followingUser(defaultUser2)
                .followedUser(defaultUser1)
                .build());
    }
}