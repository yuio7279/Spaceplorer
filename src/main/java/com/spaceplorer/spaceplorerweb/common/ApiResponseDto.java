package com.spaceplorer.spaceplorerweb.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
public class ApiResponseDto {
    private final String message;
    private final Integer statusCode;

}
