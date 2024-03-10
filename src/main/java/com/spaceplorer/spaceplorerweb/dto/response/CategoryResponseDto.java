package com.spaceplorer.spaceplorerweb.dto.response;

import com.spaceplorer.spaceplorerweb.domain.Category;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryResponseDto {

    private Long id;

    private String categoryName;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
    }
}