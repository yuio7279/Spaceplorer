package com.spaceplorer.spaceplorerweb.domain;

import com.spaceplorer.spaceplorerweb.dto.request.UserRequestDto;
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
    private String userName;


    @Column(nullable = false, unique = true)
    private String socialId;

    //naver, kakao,,,
    @Column(nullable = false)
    private String socialProvider;

    //실제 사용하는 이메일, 필요할 때 입력
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    //회원 조회
    public User(UserRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.socialId = requestDto.getSocialId();
        this.socialProvider = requestDto.getSocialProvider();
        this.email = requestDto.getEmail();
        this.profileImageUrl = requestDto.getProfileImageUrl();
    }

}
