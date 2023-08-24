package com.example.demo.subscribe;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    List<Subscribe> findAllByFollowingUser(User user);

    List<Subscribe> findAllByFollowedUser(User user);
}
