package com.spaceplorer.spaceplorerweb.auth.social;

import com.spaceplorer.spaceplorerweb.auth.social.strategy.SocialLoginSelector;
import com.spaceplorer.spaceplorerweb.auth.social.strategy.SocialLoginStrategy;
import com.spaceplorer.spaceplorerweb.auth.social.userdetails.SocialUserDetails;
import com.spaceplorer.spaceplorerweb.dto.request.UserSaveRequestDto;
import com.spaceplorer.spaceplorerweb.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


//DB에 저장

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


    private final SocialLoginSelector socialLoginSelector;
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {


            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            log.debug("[registrationId:{}]", registrationId);

            SocialLoginStrategy socialLoginStrategy = socialLoginSelector.select(registrationId);
            log.debug("[social login strategy:{}]", socialLoginStrategy.getClass().getSimpleName());
            OAuth2User oAuth2User = super.loadUser(userRequest);
            SocialUserDetails socialUserDetails = socialLoginStrategy.loadUser(oAuth2User.getAttributes());
            log.debug("[socialUserDetails:{}]", socialUserDetails);
            //DB에 사용자 정보 저장
            UserSaveRequestDto requestDto = new UserSaveRequestDto(socialUserDetails);
            userService.saveUser(requestDto);

            return oAuth2User;
        }
        catch (Exception e) {
                    log.error("Error during social user processing: {}", e.getMessage(), e);
                    throw e;
        }

    }
}
