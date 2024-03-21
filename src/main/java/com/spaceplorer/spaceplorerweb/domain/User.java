package com.spaceplorer.spaceplorerweb.domain;

import com.spaceplorer.spaceplorerweb.dto.request.UserSaveRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private Long socialId;

    //naver, kakao,,,
    @Column(nullable = false)
    private String socialProvider;

    @Column(nullable = false, unique = true)
    private String email;

    private String profileImage;

    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private Role role;

    //유저만 조회 시에는 관련이 없기 때문에 LAZY
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();


    //회원 저장
    public User(UserSaveRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.socialId = requestDto.getSocialId();
        this.socialProvider = requestDto.getSocialProvider();
        this.email = requestDto.getEmail();
        this.profileImage = requestDto.getProfileImage();
        this.thumbnail = requestDto.getThumbnail();
        this.role = requestDto.getRole();
    }


}
