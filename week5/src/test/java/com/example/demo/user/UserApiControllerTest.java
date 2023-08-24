package com.example.demo.user;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext context;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        userRepository.deleteAll();
    }
    final String nickname = "nickname";
    final String password = "password";
    final String name = "name";
    final String website = "website";
    final String bio = "bio";
    final String email = "email";
    final String phone = "phone";
    final String gender = "gender";
    final UserAddRequestDto requestDto = UserAddRequestDto.builder()
            .nickname(nickname).password(password).name(name)
            .website(website).bio(bio).email(email)
            .phone(phone).gender(gender).build();


    @DisplayName("[POST /api/v1/users] signUp: 회원 가입에 성공")
    @Test
    void signUp() throws Exception {
        // Given
        final String url = "/api/v1/users";

        // When
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isCreated());
        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getNickname()).isEqualTo(nickname);
        assertThat(users.get(0).getName()).isEqualTo(name);
        assertThat(users.get(0).getWebsite()).isEqualTo(website);
        assertThat(users.get(0).getBio()).isEqualTo(bio);
        assertThat(users.get(0).getEmail()).isEqualTo(email);
        assertThat(users.get(0).getPhone()).isEqualTo(phone);
        assertThat(users.get(0).getGender()).isEqualTo(gender);

    }

    @DisplayName("[GET /api/v1/users/{user-id}] findUserById: id로 회원 조회에 성공")
    @Test
    void findUserById() throws Exception {
        // Given
        final String url = "/api/v1/users/{user-id}";
        User savedUser = userRepository.save(requestDto.toEntity("password"));

        // When
        ResultActions resultActions = mockMvc.perform(get(url, savedUser.getId()))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.website").value(website))
                .andExpect(jsonPath("$.bio").value(bio))
                .andExpect(jsonPath("$.gender").value(gender));

    }

    @DisplayName("[GET /api/v1/users?nickname=nickname]findUserByNickname: 닉네임으로 회원 조회에 성공")
    @Test
    void findUserByNickname() throws Exception {
        final String url = "/api/v1/users?nickname=nickname";
        User savedUser = userRepository.save(requestDto.toEntity("password"));

        // When
        ResultActions resultActions = mockMvc.perform(get(url))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.website").value(website))
                .andExpect(jsonPath("$.bio").value(bio))
                .andExpect(jsonPath("$.gender").value(gender));
    }

    @DisplayName("[DELETE /api/v1/users/{user-id}] deleteUser: 회원 삭제에 성공")
    @Test
    void deleteUser() throws Exception {
        // Given
        final String url = "/api/v1/users/{user-id}";
        User savedUser = userRepository.save(requestDto.toEntity("password"));

        // When
        ResultActions resultActions = mockMvc.perform(delete(url, savedUser.getId()))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());

        List<User> users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    @DisplayName("[PUT /api/v1/users/{user-id}] updateUser: 회원 정보 수정에 성공")
    @Test
    void updateUser() throws Exception {
        // Given
        final String url = "/api/v1/users/{user-id}";
        User savedUser = userRepository.save(requestDto.toEntity("password"));

        // When

        final String newNickname = "newNickname";
        final String newName = "newName";
        final String newWebsite = "newWebsite";
        final String newBio = "newBio";
        final String newGender = "newGender";

        UserUpdateRequestDto updateRequestDto = UserUpdateRequestDto.builder()
                .nickname(newNickname).website(newWebsite).gender(newGender).bio(newBio).name(newName).build();

        ResultActions resultActions = mockMvc.perform(put(url, savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updateRequestDto)))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk());

        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getNickname()).isEqualTo(newNickname);
        assertThat(users.get(0).getName()).isEqualTo(newName);
        assertThat(users.get(0).getWebsite()).isEqualTo(newWebsite);
        assertThat(users.get(0).getBio()).isEqualTo(newBio);
        assertThat(users.get(0).getGender()).isEqualTo(newGender);
    }
}