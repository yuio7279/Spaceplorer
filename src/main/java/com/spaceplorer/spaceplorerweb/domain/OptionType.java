package com.spaceplorer.spaceplorerweb.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OptionType {
    SPACESHIP("우주선"),
    ENTERTAINMENT("엔터테인"),
    HOTEL("호텔"),
    LANDMARK("랜드마크");


    private final String value;

}
