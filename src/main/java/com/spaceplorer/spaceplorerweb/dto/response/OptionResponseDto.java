package com.spaceplorer.spaceplorerweb.dto.response;

import com.spaceplorer.spaceplorerweb.domain.OptionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OptionResponseDto {
    private Long id;

    //옵션 명
    private String optionName;

    //옵션 가격
    private Long cost;

    //옵션 타입
    private OptionType optionType;
}
