package com.spaceplorer.spaceplorerweb.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReceiptResponseDto {

    private Long id;
    private Long totalPrice;
    private List<SelectedOptionResponseDto> selectedOptionList;
}
