package com.spaceplorer.spaceplorerweb.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
    private Long id;
    private String userId;
    private String socialId;
    private String socialProvider;
    private String email;
    private String profileImageUrl;
}
