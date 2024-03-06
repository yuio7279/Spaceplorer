package com.spaceplorer.spaceplorerweb;


import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.common.Messages;
import com.spaceplorer.spaceplorerweb.domain.User;
import com.spaceplorer.spaceplorerweb.dto.request.UserRequestDto;
import com.spaceplorer.spaceplorerweb.dto.response.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/*
 * ResponseEntity<UserResponseDto> 로 리턴을 받아야하는데...
 * 유저 세팅이 너무 오래걸려서 만들었다.
 */
public class DataInit {
    private List<UserRequestDto> userRequestDtoList;

    public DataInit() {
        inputUserList();
    }

    public void inputUserList(){
        userRequestDtoList = new ArrayList<>();
        UserRequestDto requestDto = new UserRequestDto();
        //id=0
        requestDto.setUserId("testMyId");
        requestDto.setSocialId("testMyId@daum.net");
        requestDto.setSocialProvider("kakao");
        requestDto.setEmail("testMyId@daum.net");
        requestDto.setProfileImageUrl("DAF3ACK2132");
        userRequestDtoList.add(requestDto);

        //id=1
        requestDto.setUserId("adreate");
        requestDto.setSocialId("adreate@naver.com");
        requestDto.setSocialProvider("naver");
        requestDto.setEmail("adreate@wix.com");
        requestDto.setProfileImageUrl("RK4322DCX1239S");
        userRequestDtoList.add(requestDto);

        //id=2
        requestDto.setUserId("rrr123wqe");
        requestDto.setSocialId("rrr123wqe@naver.com");
        requestDto.setSocialProvider("naver");
        requestDto.setEmail("rrr123wqe@naver.com");
        requestDto.setProfileImageUrl("CHJSDK23423%30");
        userRequestDtoList.add(requestDto);
    }


    public List<UserRequestDto> requestUserList(){
        return userRequestDtoList;

    }
    public List<ResponseEntity<UserResponseDto>> responseUserList(){
        List<User> userList = new ArrayList<>();
        for (UserRequestDto userRequestDto : userRequestDtoList) {
             userList.add(new User(userRequestDto));
        }
        List<ResponseEntity<UserResponseDto>> responseEntityList = new ArrayList<>();
        for (User user : userList) {
            responseEntityList.add(
                    ResponseEntity.ok(
                            new UserResponseDto(user,
                                    new ApiResponseDto(Messages.FOUND_USER, HttpStatus.OK.value()))));
        }


        return responseEntityList;
    }

    public UserRequestDto requestUserOne(){
        return userRequestDtoList.get(0);
    }
    public ResponseEntity<UserResponseDto> responseUserOne(){
        User user1 = new User(userRequestDtoList.get(0));

        return ResponseEntity.ok(
                new UserResponseDto(user1,
                        new ApiResponseDto(Messages.FOUND_USER, HttpStatus.OK.value())));
    }
}
