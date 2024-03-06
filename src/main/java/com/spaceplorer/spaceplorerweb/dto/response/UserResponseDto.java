package com.spaceplorer.spaceplorerweb.dto.response;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
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

    //ApiResponseDto객체로 주입받아 저장.
    private final String message;
    private final Integer statusCode;

    //유저 조회
    public UserResponseDto(User user, ApiResponseDto apiResponseDto) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.socialId = user.getSocialId();
        this.socialProvider = user.getSocialProvider();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();

        this.message = apiResponseDto.getMessage();
        this.statusCode = apiResponseDto.getStatusCode();
    }
}
