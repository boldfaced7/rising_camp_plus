package com.example.demo.subscribe;

import com.example.demo.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscribeRequestDto {

    private Long followedUserId;

    @Builder
    public SubscribeRequestDto(Long followedUserId) {
        this.followedUserId = followedUserId;
    }

    public Subscribe toEntity(User followingUser, User followedUser) {
        return Subscribe.builder()
                .followingUser(followingUser)
                .followedUser(followedUser)
                .build();
    }
}
