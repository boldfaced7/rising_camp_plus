package com.example.demo.user;

import com.example.demo.post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "password")
    private String password;
    private String name;
    private String website;
    private String bio;
    private String email;
    private String phone;
    private String gender;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(String nickname, String password, String name, String website,
                String bio, String email, String phone, String gender) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.bio = bio;
        this.gender = gender;
    }

    public void update(String nickname, String name, String website,
                       String bio, String gender) {
        this.nickname = nickname;
        this.name = name;
        this.website = website;
        this.bio = bio;
        this.gender = gender;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
