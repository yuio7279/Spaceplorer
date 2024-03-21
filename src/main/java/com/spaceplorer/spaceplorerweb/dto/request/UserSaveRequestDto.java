package com.spaceplorer.spaceplorerweb.dto.request;

import com.spaceplorer.spaceplorerweb.auth.social.userdetails.SocialUserDetails;
import com.spaceplorer.spaceplorerweb.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserSaveRequestDto {
    private String userName;
    private Long socialId;
    private String socialProvider;
    private String email;
    private String profileImage;
    private String thumbnail;
    private Role role;



    public UserSaveRequestDto(SocialUserDetails socialUserDetails) {
        this.userName = socialUserDetails.getUserName();
        this.socialId = socialUserDetails.getSocialId();
        this.socialProvider = socialUserDetails.getSocialProvider();
        this.email = socialUserDetails.getEmail();
        this.profileImage = socialUserDetails.getProfileImage();
        this.thumbnail = socialUserDetails.getThumbnail();
        this.role = Role.OAUTH2_USER;
    }
}
