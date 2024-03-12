package com.spaceplorer.spaceplorerweb.common;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.spaceplorer.spaceplorerweb.common.Messages.NOT_FOUND_DATA;
import static org.springframework.http.HttpStatus.NOT_FOUND;

//응답코드가 너무 길면서 계속 중복되다보니 static method를 만들었다.

@Component
@RequiredArgsConstructor
public class Util {

    private final ModelMapper modelMapper;


    //응답생성기, entity를 empty체크 후 responseDto로 변환하여 응답한다.
    public <T, R> ResponseEntity<ApiResponseDto<R>> generateDtoResponse(Logger log, HttpStatus status, Optional<T> entity, Class<R> dtoType, String message) {
        log.info("[GenerateDtoResponse entity:{} dtoType:{}]", entity, dtoType);
        Optional<ResponseEntity<ApiResponseDto<R>>> notFoundResponse = emptyCheckEntity(log, entity);
        return notFoundResponse.orElseGet(() -> responseGenerator(status, extractEntityToResponseDto(log, entity, dtoType), message, status.value()));
    }

    //오버로딩, 리스트용
    public <T, R> ResponseEntity<ApiResponseDto<List<R>>> generateDtoResponse(Logger log, HttpStatus status, List<T> entityList, Class<R> dtoType, String message) {
        log.info("[GenerateDtoResponse entity:{} dtoType:{}]", entityList, dtoType);
        Optional<ResponseEntity<ApiResponseDto<List<R>>>> notFoundResponse = emptyCheckEntity(log, entityList);
        return notFoundResponse.orElseGet(() -> responseGenerator(status, extractEntityToResponseDto(log, entityList, dtoType), message, status.value()));
    }


    public <T> ResponseEntity<ApiResponseDto<T>> responseGenerator(HttpStatus code, T data, String message, int codeToInt) {
        return ResponseEntity.status(code).body(new ApiResponseDto<>(data, message, codeToInt));
    }


    public <T, R> Optional<ResponseEntity<ApiResponseDto<List<R>>>> emptyCheckEntity(Logger log, List<T> entityList) {
        if(entityList.isEmpty()){
            log.error("[Entity is Empty {}]", entityList);
            return Optional.of(responseGenerator(NOT_FOUND, null, NOT_FOUND_DATA, NOT_FOUND.value()));
        }
        return Optional.empty();
    }

    public <T, R> Optional<ResponseEntity<ApiResponseDto<R>>> emptyCheckEntity(Logger log, Optional<T> entity) {
        if(entity.isEmpty()){
            log.error("[Entity is Empty {}]", entity);
            return Optional.of(responseGenerator(NOT_FOUND, null, NOT_FOUND_DATA, NOT_FOUND.value()));
        }
        return Optional.empty();
    }


    private <T, R> R extractEntityToResponseDto(Logger log, Optional<T> entity, Class<R> dtoType) {
        log.info("[Entity :{}->dtoType :{}]",entity, dtoType.toString());
        R responseDto = modelMapper.map(entity.get(), dtoType);
        log.info("[Created responseDto:{}]", responseDto);
        return responseDto;
    }
    private <T, R> List<R> extractEntityToResponseDto(Logger log, List<T> entityList, Class<R> dtoType) {
        log.info("[Entity :{}->dtoType :{}]",entityList, dtoType.toString());
        List<R> responseDtoList = entityList.stream().map(
                entity -> modelMapper.map(entity, dtoType)).toList();
        log.info("[Created responseDto:{}]", responseDtoList);
        return responseDtoList;
    }



}


