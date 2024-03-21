package com.spaceplorer.spaceplorerweb.dto.response;

import com.spaceplorer.spaceplorerweb.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//게시글 리스트 전달
public class BoardResponseDto {
    private Long id;
    private String title;
    private String author;
    private Integer likeCount;
    private Long views;
    private String categoryName;
    private String cityName;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.author = board.getUser().getUserName();
        this.likeCount = board.getLikeCount();
        this.views = board.getViews();
        this.categoryName = board.getCategory().getCategoryName();
        this.cityName = board.getCity().getCityName();
    }
}
