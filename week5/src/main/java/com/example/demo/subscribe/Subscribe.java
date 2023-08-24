package com.example.demo.subscribe;

import com.example.demo.BaseTimeEntity;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "SubscribeUk",
                        columnNames = {"followingUserId", "followedUserId"}
                )
        }
)
public class Subscribe extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "followingUserId")
    @ManyToOne
    private User followingUser;

    @JoinColumn(name = "followedUserId")
    @ManyToOne
    private User followedUser;

    @Builder
    public Subscribe(User followingUser, User followedUser) {
        this.followingUser = followingUser;
        this.followedUser = followedUser;
    }
}
