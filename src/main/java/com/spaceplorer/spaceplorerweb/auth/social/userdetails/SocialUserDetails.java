package com.spaceplorer.spaceplorerweb.auth.social.userdetails;

public interface SocialUserDetails{
    public Long getSocialId();

    public String getUserName();

    public String getEmail();

    public String getPhone();

    public String getThumbnail();

    public String getProfileImage();

    public String getSocialProvider();

}
