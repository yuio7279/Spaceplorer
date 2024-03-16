package com.spaceplorer.spaceplorerweb.dto.response;

import com.spaceplorer.spaceplorerweb.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private Long views;
    private String aliasName;
    private String cityName;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.views = board.getViews();
        this.cityName = board.getCity().getCityName();
        this.aliasName = board.getCity().getPlanet().getAlias();
    }
}
