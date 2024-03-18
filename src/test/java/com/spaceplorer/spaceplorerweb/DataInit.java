package com.spaceplorer.spaceplorerweb;


import com.spaceplorer.spaceplorerweb.dto.request.UserSaveRequestDto;

import java.util.ArrayList;
import java.util.List;

public class DataInit {
    private List<UserSaveRequestDto> userRequestDtoList;

    public List<UserSaveRequestDto> getUserRequestDtoList() {
        return userRequestDtoList;
    }

    public DataInit() {
        inputUserList();
    }

    public void inputUserList(){
        userRequestDtoList = new ArrayList<>();
        UserSaveRequestDto requestDto = new UserSaveRequestDto();
        //id=0
        requestDto.setSocialId(3213213L);
        requestDto.setSocialProvider("kakao");
        requestDto.setEmail("testMyId@daum.net");
        requestDto.setProfileImage("DAF3ACK2132.JPG");
        requestDto.setThumbnail("DQ2132D.JPG");
        requestDto.setUserName("testMyId");
        requestDto.setPhone("010-3213-3213");
        userRequestDtoList.add(requestDto);

        //id=1
        requestDto.setSocialId(8823413L);
        requestDto.setSocialProvider("naver");
        requestDto.setProfileImage("DAF3ACK2132.JPG");
        requestDto.setEmail("adreate@wix.com");
        requestDto.setThumbnail("DQ2132D.JPG");
        requestDto.setUserName("adreate");
        requestDto.setPhone("010-3773-3233");
        userRequestDtoList.add(requestDto);

        //id=2
        requestDto.setSocialId(774783L);
        requestDto.setThumbnail("DQ2132D.JPG");
        requestDto.setUserName("rrr123wqe");
        requestDto.setPhone("010-1193-7233");
        requestDto.setSocialProvider("naver");
        requestDto.setEmail("rrr123wqe@naver.com");
        requestDto.setProfileImage("DWJ328DW.JPG");
        userRequestDtoList.add(requestDto);

        //id=3
        requestDto.setSocialId(475883L);
        requestDto.setThumbnail("DQ2132D.JPG");
        requestDto.setUserName("rrr123wqe");
        requestDto.setPhone("010-1093-7263");
        requestDto.setSocialProvider("naver");
        requestDto.setEmail("rrr123wqe@kakao.net");
        requestDto.setProfileImage("DWJ328DW.JPG");

        //id=4
        requestDto.setSocialId(643883L);
        requestDto.setThumbnail("DQ2132D.JPG");
        requestDto.setUserName("rrr123wqe6666666");
        requestDto.setPhone("010-1093-7264");
        requestDto.setSocialProvider("naver");
        requestDto.setEmail("rrr123wqe6666666@daum.net");
        requestDto.setProfileImage("DWJ328DW.JPG");
        userRequestDtoList.add(requestDto);
    }

}
