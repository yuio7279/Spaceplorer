package com.spaceplorer.spaceplorerweb.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CityResponseDto {


    private Long id;

    //도시 명
    private String cityName;

    //도시 설명
    private String description;


    public CityResponseDto(Long id, String cityName, String description) {
        this.id = id;
        this.cityName = cityName;
        this.description = description;
    }

}
