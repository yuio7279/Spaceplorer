package com.spaceplorer.spaceplorerweb.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HotelResponseDto {


    private  Long id;

    private  String hotelName;

    //호텔 점수 1~5
    private  Long rating;

    //설명
    private  String description;

}
