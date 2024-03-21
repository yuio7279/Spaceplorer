package com.spaceplorer.spaceplorerweb.domain.spaceship;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShipClass {
    EXPLORER_CLASS("EXPLORER_CLASS", "VIP"),
    PERSONAL_CLASS("PERSONAL_CLASS", "일반");

    private final String key;
    private final String value;
}
