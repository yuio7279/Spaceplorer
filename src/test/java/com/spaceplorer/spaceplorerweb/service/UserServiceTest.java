/*
package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.DataInit;
import com.spaceplorer.spaceplorerweb.common.Messages;
import com.spaceplorer.spaceplorerweb.dto.response.UserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserService userService; // UserService에 userRepository 모의 객체 주입
    private final DataInit dataInit = new DataInit();


    @Test
    void id로유저한명조회(){

        //id = 0L의 유저정보가 담긴다.
*/
/*        ResponseEntity<UserResponseDto> responseEntity = dataInit.responseUserOne();
        Long id = responseEntity.getBody().getId();

        //given
        when(userService.findById(id)).thenReturn(responseEntity);

        //when
        ResponseEntity<UserResponseDto> found = userService.findById(id);
        Assertions.assertThat((found.getBody()).getId()).isEqualTo(0L);*//*


    }
    @Test
    void id로유저한명조회X(){

        //given
        Long failId = -1L;
        when(userService.findById(failId)).thenThrow(new NoSuchElementException(Messages.NOT_FOUND_USER));

        //when & then
        assertThrows(NoSuchElementException.class, () -> userService.findById(failId));
    }
}
*/
