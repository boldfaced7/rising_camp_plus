package com.example.demo.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAddRequestDto {
    private String nickname;
    private String password;
    private String name;
    private String website;
    private String bio;
    private String email;
    private String phone;
    private String gender;

    @Builder
    public UserAddRequestDto(String nickname, String password, String name, String website,
                             String bio, String email, String phone, String gender) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.website = website;
        this.bio = bio;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }

    public User toEntity(String encodedPassword) {
        return User.builder()
                .nickname(nickname)
                .password(encodedPassword)
                .name(name)
                .website(website)
                .bio(bio)
                .email(email)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
