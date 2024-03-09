package com.spaceplorer.spaceplorerweb.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto<T> {
    private  T data;
    private final String message;
    private final Integer statusCode;

    public ApiResponseDto(T data, String message, Integer statusCode) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public ApiResponseDto(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
