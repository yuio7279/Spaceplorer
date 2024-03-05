package com.spaceplorer.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //myUserId@email.com
    //myUserId, myUserId#1, myUserId#2,,,
    @Column(nullable = false, unique = true)
    private String userId;

    //실제 사용하는 이메일, 필요할 때 입력
    @Column(nullable = true, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String socialId;

    //naver, kakao,,,
    @Column(nullable = false)
    private String socialProvider;

    @Column(nullable = true, unique = true)
    private String profileImageUrl;

}
