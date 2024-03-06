package com.spaceplorer.spaceplorerweb.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
@ToString
public class NaverAuth implements Auth {

    private final String clientId;
    private final String redirectUri;
    private final String responseType;
    private final String scope;
    private final String authorizationUrl;
    private final String AUTHORIZATION_REQUEST_URL_FORMAT_NAVER = "%s?client_id=%s&redirect_uri=%s&response_type=%s&scope=%s";


    public String getAuthorizationRequestUrl(){

        String authorizationRequestUrl = String.format(AUTHORIZATION_REQUEST_URL_FORMAT_NAVER,
                authorizationUrl, clientId, redirectUri, responseType, scope);

        log.info("method=getAuthorizationRequestUrl, authorizationRequestUrl={}",authorizationRequestUrl);
        return authorizationRequestUrl;
    }
}
