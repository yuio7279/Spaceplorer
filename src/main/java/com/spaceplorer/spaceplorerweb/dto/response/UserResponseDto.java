package com.spaceplorer.spaceplorerweb.dto.response;

import com.spaceplorer.spaceplorerweb.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {

    private final Long id;
    private final String userId;
    private final String socialId;
    private final String socialProvider;
    private final String email;
    private final String profileImageUrl;


    //유저 조회
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.socialId = user.getSocialId();
        this.socialProvider = user.getSocialProvider();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
