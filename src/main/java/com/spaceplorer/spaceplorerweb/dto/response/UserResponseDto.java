package com.spaceplorer.spaceplorerweb.dto.response;

import com.spaceplorer.spaceplorerweb.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private  Long id;
    private  String userName;
    private  Long socialId;
    private  String socialProvider;
    private  String email;
    private  String profileImageUrl;


    //유저 조회
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.socialId = user.getSocialId();
        this.socialProvider = user.getSocialProvider();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImage();
    }
}
