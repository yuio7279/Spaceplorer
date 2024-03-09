package com.spaceplorer.spaceplorerweb.dto.response;

import com.spaceplorer.spaceplorerweb.domain.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

    private final Long id;

    private final String categoryName;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
    }
}