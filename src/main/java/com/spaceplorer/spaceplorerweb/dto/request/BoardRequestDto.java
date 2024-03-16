package com.spaceplorer.spaceplorerweb.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {

    public String title;
    public String content;

    //카테고리 이름으로 게시판 속성을 확인
    public String categoryName;

    public String cityName;

}
