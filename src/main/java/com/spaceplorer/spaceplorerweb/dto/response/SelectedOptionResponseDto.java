package com.spaceplorer.spaceplorerweb.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SelectedOptionResponseDto {

    private Long id;
    private OptionResponseDto option;

}
