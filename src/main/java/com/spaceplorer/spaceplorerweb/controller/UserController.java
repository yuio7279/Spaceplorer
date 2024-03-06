package com.spaceplorer.spaceplorerweb.controller;

import com.spaceplorer.spaceplorerweb.dto.response.UserResponseDto;
import com.spaceplorer.spaceplorerweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //유저 한명 조회
    @GetMapping("/{userId}")
    ResponseEntity<UserResponseDto> findById(@PathVariable Long userId){

        return userService.findById(userId);
    }

}
