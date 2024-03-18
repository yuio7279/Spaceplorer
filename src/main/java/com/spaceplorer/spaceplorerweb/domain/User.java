package com.spaceplorer.spaceplorerweb.domain;

import com.spaceplorer.spaceplorerweb.auth.social.userdetails.SocialUserDetails;
import com.spaceplorer.spaceplorerweb.dto.request.UserSaveRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @Column(nullable = false, unique = true)
    private String phone;

    private String profileImage;

    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private Role role;

    //회원 저장
    public User(UserSaveRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.socialId = requestDto.getSocialId();
        this.socialProvider = requestDto.getSocialProvider();
        this.email = requestDto.getEmail();
        this.profileImage = requestDto.getProfileImage();
        this.phone = requestDto.getPhone();
        this.thumbnail = requestDto.getThumbnail();
        this.role = requestDto.getRole();
    }


}
