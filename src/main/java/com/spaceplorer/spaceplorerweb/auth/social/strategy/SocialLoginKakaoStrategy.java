package com.spaceplorer.spaceplorerweb.auth.social.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceplorer.spaceplorerweb.auth.social.userdetails.SocialKakaoUserDetails;
import com.spaceplorer.spaceplorerweb.auth.social.userdetails.SocialUserDetails;

import java.util.Map;

public class SocialLoginKakaoStrategy implements SocialLoginStrategy {
    @Override
    public SocialUserDetails loadUser(Map<String, Object> attributes) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(attributes, SocialKakaoUserDetails.class);
    }
}
