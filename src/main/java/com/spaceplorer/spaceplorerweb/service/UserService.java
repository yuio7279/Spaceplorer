package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.common.Messages;
import com.spaceplorer.spaceplorerweb.domain.User;
import com.spaceplorer.spaceplorerweb.dto.response.UserResponseDto;
import com.spaceplorer.spaceplorerweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;


    public ResponseEntity<UserResponseDto> findById(Long id) {

        User foundUser = userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(Messages.NOT_FOUND_USER));


        ApiResponseDto apiResponseDto = new ApiResponseDto(Messages.FOUND_USER, HttpStatus.OK.value());


        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto(foundUser, apiResponseDto));
    }
}
