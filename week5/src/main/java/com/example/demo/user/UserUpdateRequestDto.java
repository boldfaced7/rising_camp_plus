package com.example.demo.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String nickname;
    private String name;
    private String website;
    private String bio;
    private String gender;

    @Builder
    public UserUpdateRequestDto(String nickname, String name, String website, String bio, String gender) {
        this.nickname = nickname;
        this.name = name;
        this.website = website;
        this.bio = bio;
        this.gender = gender;
    }
}
