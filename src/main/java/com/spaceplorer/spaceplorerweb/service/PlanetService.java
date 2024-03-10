package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.common.Messages;
import com.spaceplorer.spaceplorerweb.domain.City;
import com.spaceplorer.spaceplorerweb.domain.Planet;
import com.spaceplorer.spaceplorerweb.dto.response.CityResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.PlanetNameCityListResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.PlanetResponseDto;
import com.spaceplorer.spaceplorerweb.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<ApiResponseDto<PlanetResponseDto>> getPlanetById(Long id){
        if(id == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponseDto<>(null,
                            Messages.INVALID_ID, HttpStatus.NOT_FOUND.value()));
        }

        Optional<Planet> entity = planetRepository.findById(id);
        if(entity.isEmpty()){
            log.error("[Not found planet id:{}]",id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponseDto<>(null,
                            Messages.NOT_FOUND_PLANET, HttpStatus.NOT_FOUND.value()));
        }

        log.info("[Load to Entity planet:{}]",entity.get());
        PlanetResponseDto responseDto = modelMapper.map(entity.get(), PlanetResponseDto.class);
        log.info("[Created dto planet:{}]",responseDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseDto<>(responseDto,
                        Messages.FOUND_PLANET, HttpStatus.OK.value()));
    }

    public ResponseEntity<ApiResponseDto<PlanetNameCityListResponseDto>> getPlanetNameCityListByPlanetId(Long id) {
        if(id == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponseDto<>(null,
                            Messages.INVALID_ID, HttpStatus.NOT_FOUND.value()));
        }
        Optional<Planet> entity = planetRepository.findById(id);
        if(entity.isEmpty()){
            log.error("[Not found planet id:{}]",id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponseDto<>(null,
                            Messages.NOT_FOUND_PLANET, HttpStatus.NOT_FOUND.value()));
        }
        String planetName = entity.get().getPlanetName();
        List<City> cityEntityList = entity.get().getCityList();

        List<CityResponseDto> cityList = new ArrayList<>();

        for (City city : cityEntityList) {
            cityList.add(new CityResponseDto(city.getId(),city.getCityName(),city.getDescription()));
        }
        PlanetNameCityListResponseDto responseDto = new PlanetNameCityListResponseDto(id, planetName, cityList);

        log.info("[Created dto PlanetNameCityList:{}]",responseDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseDto<>(responseDto,
                        Messages.FOUND_CITY, HttpStatus.OK.value()));
    }
}

