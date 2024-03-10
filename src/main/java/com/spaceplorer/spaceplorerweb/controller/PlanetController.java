package com.spaceplorer.spaceplorerweb.controller;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.PlanetNameCityListResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.PlanetResponseDto;
import com.spaceplorer.spaceplorerweb.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/planets")
public class PlanetController {
    private final PlanetService planetService;

    @GetMapping("/{planet_id}")
    public ResponseEntity<ApiResponseDto<PlanetResponseDto>> getPlanetById(
            @PathVariable("planet_id") Long id){

        return planetService.getPlanetById(id);
    }
    @GetMapping("/{planet_id}/cities")
    public ResponseEntity<ApiResponseDto<PlanetNameCityListResponseDto>> getPlanetNameCityListByPlanetId(
            @PathVariable("planet_id") Long id){

        return planetService.getPlanetNameCityListByPlanetId(id);
    }
}
