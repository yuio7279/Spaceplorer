package com.spaceplorer.spaceplorerweb.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PlanetResponseDto {

    private  Long id;

    //행성명
    private  String planetName;

    //온도
    private  String temperature;

    //자전주기
    private  String cycle;

    //지구와의 거리
    private  Long distanceFromEarth;

    //행성에 대한 설명
    private  String description;

    //hhms로만 도착할 수 있는 행성인지 여부
    private  Boolean requiredHhms;

    //행성에 존재하는 도시
    private  List<CityResponseDto> cityList;

}
