package com.galaxy.novelit.author.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_uuid",length = 36, nullable = false)
    private String userUUID;

    @Column(name = "email", length = 320, nullable = false)
    private String email;

    @Column(name = "nickname", length = 16, nullable = false)
    private String nickname;

    @Builder
    public User(String userUUID, String email, String nickname) {
        this.userUUID = userUUID;
        this.email = email;
        this.nickname = nickname;
    }

    @Builder
    public User(String email, String nickname) {
        this.userUUID = String.valueOf(UUID.randomUUID());
        this.email = email;
        this.nickname = nickname;
    }
}
