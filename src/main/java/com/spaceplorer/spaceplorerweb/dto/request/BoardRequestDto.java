package com.spaceplorer.spaceplorerweb.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {

    private String title;
    private String content;

    //카테고리 이름으로 게시판 속성을 확인
    private String categoryName;

    private String cityName;

    private List<String> imageUrlList;

}
