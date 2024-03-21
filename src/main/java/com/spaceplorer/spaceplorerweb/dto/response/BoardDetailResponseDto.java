package com.spaceplorer.spaceplorerweb.dto.response;

import com.spaceplorer.spaceplorerweb.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//게시판 상세 글 응답용 객체
@Setter
@Getter
@NoArgsConstructor
public class BoardDetailResponseDto {

    private Long id;
    private String author;
    private String title;
    private String content;
    private String categoryName;
    private String cityName;
    private Long views;
    private Integer likeCount;

    private String thumbnail;
    private List<ImageResponseDto> imageList;

    public BoardDetailResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.views = board.getViews();
        this.cityName = board.getCity().getCityName();
        this.categoryName = board.getCategory().getCategoryName();
        this.likeCount = board.getLikeCount();
        this.author = board.getUser().getUserName();
        this.thumbnail = board.getUser().getThumbnail();
        this.imageList = board.getImageList().stream().map(
                ImageResponseDto::new).toList();
    }
}
