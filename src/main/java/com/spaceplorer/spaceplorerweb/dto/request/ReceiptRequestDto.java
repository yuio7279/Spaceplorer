package com.spaceplorer.spaceplorerweb.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ReceiptRequestDto {

    // 총 구매금액(서버에서 검증이 필요)
    public Long totalPrice;

    //옵셔 id 리스트
    public List<Long> optionIdList;
}
