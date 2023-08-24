package com.example.demo.subscribe;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final UserRepository userRepository;

    public void follow(Long followedUserId) {
        User followingUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User followedUser = userRepository.findById(followedUserId).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + followedUserId));

        Subscribe subscribe = Subscribe.builder()
                .followedUser(followedUser)
                .followingUser(followingUser)
                .build();
        subscribeRepository.save(subscribe);
    }

    @Transactional
    public void unfollow(Long followedUserId) {
        User followingUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User followedUser = userRepository.findById(followedUserId).orElseThrow(() ->
                new IllegalArgumentException("Not found: " + followedUserId));

        Subscribe subscribe = Subscribe.builder()
                .followedUser(followedUser)
                .followingUser(followingUser)
                .build();
        subscribeRepository.delete(subscribe);
    }

    public List<SubscribeFollowerResponseDto> followers(Long id) {
        User targetUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return subscribeRepository.findAllByFollowingUser(targetUser)
                .stream().map(subscribe -> new SubscribeFollowerResponseDto(subscribe))
                .toList();
    }

    public List<SubscribeFollowingResponseDto> followings(Long id) {
        User targetUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return subscribeRepository.findAllByFollowedUser(targetUser)
                .stream().map(subscribe -> new SubscribeFollowingResponseDto(subscribe))
                .toList();
    }
}
