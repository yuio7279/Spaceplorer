package com.spaceplorer.spaceplorerweb.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class LandmarkResponseDto {

    private Long id;

    private String landmarkName;

    private String title;

    private String description;

}
