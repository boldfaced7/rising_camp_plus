package com.example.demo.subscribe;

import lombok.Getter;

@Getter
public class SubscribeFollowerResponseDto {
    private String followerNickname;
    private Long followerId;

    public SubscribeFollowerResponseDto(Subscribe subscribe) {
        this.followerId = subscribe.getFollowingUser().getId();
        this.followerNickname = subscribe.getFollowingUser().getNickname();
    }
}

