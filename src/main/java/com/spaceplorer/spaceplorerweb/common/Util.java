package com.spaceplorer.spaceplorerweb.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//응답코드가 너무 길면서 계속 중복되다보니 static method를 만들었다.
public class Util {


    public static <T> ResponseEntity<ApiResponseDto<T>> responseGenerator(HttpStatus code, T data, String message, int codeToInt) {
        return ResponseEntity.status(code).body(new ApiResponseDto<>(data, message, codeToInt));
    }


}


