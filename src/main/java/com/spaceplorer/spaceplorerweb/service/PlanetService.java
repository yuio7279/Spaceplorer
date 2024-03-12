package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.common.Util;
import com.spaceplorer.spaceplorerweb.domain.Planet;
import com.spaceplorer.spaceplorerweb.dto.response.PlanetResponseDto;
import com.spaceplorer.spaceplorerweb.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.spaceplorer.spaceplorerweb.common.Messages.FOUND_PLANET;
import static org.springframework.http.HttpStatus.OK;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;
    private final Util util;

    public ResponseEntity<ApiResponseDto<PlanetResponseDto>> getPlanetById(Long id){

        Optional<Planet> entity = planetRepository.findById(id);
        return util.generateDtoResponse(log, OK, entity, PlanetResponseDto.class, FOUND_PLANET);

    }
}

