package com.spaceplorer.spaceplorerweb.controller;


import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.CityResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.EntertainmentResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.HotelResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.LandmarkResponseDto;
import com.spaceplorer.spaceplorerweb.service.ActInCityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 해당 도시안에서 즐길 수 있는 활동들을 다루기 위한 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/planets/{planet_id}/cities/{city_id}")
public class CityController {

    private final ActInCityService actInCityService;

    //도시조회
    @GetMapping()
    public ResponseEntity<ApiResponseDto<CityResponseDto>> getCity(
            @PathVariable("city_id") Long cId,
            @PathVariable("planet_id") Long pId){

        return actInCityService.getCity(pId, cId);
    }

//엔터테인조회
    @GetMapping("/entertainments")
    public ResponseEntity<ApiResponseDto<List<EntertainmentResponseDto>>> getEntertainmentList(
            @PathVariable("city_id") Long cId,
            @PathVariable("planet_id") Long pId){

        return actInCityService.getEntertainmentList(pId, cId);
    }
    //호텔조회
    @GetMapping("/hotels")
    public ResponseEntity<ApiResponseDto<List<HotelResponseDto>>> getHotelList(
            @PathVariable("city_id") Long cId,
            @PathVariable("planet_id") Long pId){

        return actInCityService.getHotelList(pId, cId);
    }
    //랜드마크조회
    @GetMapping("/landmarks")
    public ResponseEntity<ApiResponseDto<List<LandmarkResponseDto>>> getLandmarkList(
            @PathVariable("city_id") Long cId,
            @PathVariable("planet_id") Long pId){

        return actInCityService.getLandmarkList(pId, cId);
    }
}
