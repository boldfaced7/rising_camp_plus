package com.example.demo.subscribe;

import lombok.Getter;

@Getter
public class SubscribeFollowingResponseDto {

    private String followingNickname;
    private Long followingId;

    public SubscribeFollowingResponseDto(Subscribe subscribe) {
        this.followingId = subscribe.getFollowedUser().getId();
        this.followingNickname = subscribe.getFollowedUser().getNickname();
    }
}
