package com.spaceplorer.spaceplorerweb.dto.response;

import com.spaceplorer.spaceplorerweb.domain.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ImageResponseDto {
    private Long id;
    private String imageUrl;

    public ImageResponseDto(Image image) {
        this.id = image.getId();
        this.imageUrl = image.getImageUrl();
    }
}
