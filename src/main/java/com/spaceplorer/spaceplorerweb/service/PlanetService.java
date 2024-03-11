package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.domain.Planet;
import com.spaceplorer.spaceplorerweb.dto.response.PlanetResponseDto;
import com.spaceplorer.spaceplorerweb.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.spaceplorer.spaceplorerweb.common.Messages.*;
import static com.spaceplorer.spaceplorerweb.common.Util.responseGenerator;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<ApiResponseDto<Object>> getPlanetById(Long id){

        if(id == null){
            return responseGenerator(NOT_FOUND, null, INVALID_ID, NOT_FOUND.value());

        }

        Optional<Planet> entity = planetRepository.findById(id);
        if(entity.isEmpty()){
            log.error("[Not found planet id:{}]",id);

            return responseGenerator(NOT_FOUND, null, NOT_FOUND_PLANET, NOT_FOUND.value());
        }

        log.info("[Load to Entity planet:{}]",entity.get());
        PlanetResponseDto responseDto = modelMapper.map(entity.get(), PlanetResponseDto.class);
        log.info("[Created dto planet:{}]",responseDto);

        return responseGenerator(OK, responseDto, FOUND_PLANET, OK.value());

    }
}

