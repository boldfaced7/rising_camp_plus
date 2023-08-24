package com.example.demo.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String nickname;
    private String name;
    private String website;
    private String bio;
    private String gender;

    @Builder
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.name = user.getName();
        this.website = user.getWebsite();
        this.bio = user.getBio();
        this.gender = user.getGender();
    }
}
