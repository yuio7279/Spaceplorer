package com.spaceplorer.spaceplorerweb.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class PlanetNameCityListResponseDto {

    //행성 아이디
    private Long id;
    //행성 이름
    private String planetName;
    //도시리스트
    private List<CityResponseDto> cityList;

    public PlanetNameCityListResponseDto(Long id, String planetName, List<CityResponseDto> cityList) {
        this.id = id;
        this.planetName = planetName;
        this.cityList = cityList;
    }
}
