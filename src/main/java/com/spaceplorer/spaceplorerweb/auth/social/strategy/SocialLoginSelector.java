package com.spaceplorer.spaceplorerweb.auth.social.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static com.spaceplorer.spaceplorerweb.common.Messages.NOT_FOUND_DATA;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@Slf4j
public class SocialLoginSelector {

    private final String REGISTRATIONID_KAKAO = "kakao";
    private final String REGISTRATIONID_NAVER = "naver";

    public SocialLoginStrategy select(String registrationId) {
        return switch (registrationId) {
            case REGISTRATIONID_KAKAO -> new SocialLoginKakaoStrategy();
            //case REGISTRATIONID_NAVER -> new SocialLoginNaverStrategy();
            default -> {
                log.error("[Not found social login strategy, registration_id:{}]", registrationId);
                throw new ResponseStatusException(NOT_FOUND, NOT_FOUND_DATA);
            }
        };
    }
}
