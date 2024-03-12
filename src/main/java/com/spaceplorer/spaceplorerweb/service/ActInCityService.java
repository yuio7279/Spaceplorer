package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.common.Util;
import com.spaceplorer.spaceplorerweb.domain.City;
import com.spaceplorer.spaceplorerweb.domain.Entertainment;
import com.spaceplorer.spaceplorerweb.domain.Hotel;
import com.spaceplorer.spaceplorerweb.domain.Landmark;
import com.spaceplorer.spaceplorerweb.dto.response.CityResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.EntertainmentResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.HotelResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.LandmarkResponseDto;
import com.spaceplorer.spaceplorerweb.repository.CityRepository;
import com.spaceplorer.spaceplorerweb.repository.EntertainmentRepository;
import com.spaceplorer.spaceplorerweb.repository.HotelRepository;
import com.spaceplorer.spaceplorerweb.repository.LandmarkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.spaceplorer.spaceplorerweb.common.Messages.*;
import static org.springframework.http.HttpStatus.OK;


@Service
@Slf4j
@RequiredArgsConstructor
public class ActInCityService {
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    private final LandmarkRepository landmarkRepository;
    private final EntertainmentRepository entertainmentRepository;
    private final Util util;

    public ResponseEntity<ApiResponseDto<CityResponseDto>> getCity(Long pId, Long cId) {
        Optional<City> entity = cityRepository.findByPlanetIdAndCityId(pId,cId);
        return util.generateDtoResponse(log, OK, entity, CityResponseDto.class, FOUND_CITY);
    }
    public ResponseEntity<ApiResponseDto<List<EntertainmentResponseDto>>> getEntertainmentList(Long pId, Long cId) {
        List<Entertainment> entityList = entertainmentRepository.findByPlanetAndCityIdId(pId, cId);
        return util.generateDtoResponse(log, OK, entityList, EntertainmentResponseDto.class, FOUND_ACT_ENTERTAINMENT);
    }
    public ResponseEntity<ApiResponseDto<List<HotelResponseDto>>> getHotelList(Long pId, Long cId) {
        List<Hotel> entityList = hotelRepository.findByPlanetIdAndCityId(pId, cId);
      return util.generateDtoResponse(log, OK, entityList, HotelResponseDto.class, FOUND_ACT_HOTEL);
    }
    public ResponseEntity<ApiResponseDto<List<LandmarkResponseDto>>> getLandmarkList(Long pId, Long cId) {
        List<Landmark> entityList = landmarkRepository.findByPlanetIdAndCityId(pId, cId);
      return util.generateDtoResponse(log, OK, entityList, LandmarkResponseDto.class, FOUND_ACT_LANDMARK);

    }
}

