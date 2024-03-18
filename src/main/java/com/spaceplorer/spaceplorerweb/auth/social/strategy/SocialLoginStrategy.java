package com.spaceplorer.spaceplorerweb.auth.social.strategy;

import com.spaceplorer.spaceplorerweb.auth.social.userdetails.SocialUserDetails;

import java.util.Map;

public interface SocialLoginStrategy {
    SocialUserDetails loadUser(Map<String, Object> attributes);
}
