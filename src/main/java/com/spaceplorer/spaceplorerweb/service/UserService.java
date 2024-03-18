package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.domain.User;
import com.spaceplorer.spaceplorerweb.dto.request.UserSaveRequestDto;
import com.spaceplorer.spaceplorerweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;


    public void saveUser(UserSaveRequestDto userSaveRequestDto) {


        Long socialId = userSaveRequestDto.getSocialId();
        String userName = userSaveRequestDto.getUserName();

        //소셜코드로 유저가 조회 된다면 이미 가입된 유저
        if(userRepository.findBySocialId(socialId).isPresent()){
            log.debug("[Already present user:{}]",userSaveRequestDto.getUserName());
            return;
        }
        
        //이메일을 substring 해서 userName을 저장하기 때문에 중복이 userName 중복 발생가능성이 있다. userName의 중복체크
        List<User> userEntityList = userRepository.findByUserNamePattern(userName+"%");
        log.debug("[userEntityList :{}]",userEntityList);
        if(userEntityList.isEmpty()){
            User savedUser = userRepository.save(new User(userSaveRequestDto));
            log.debug("[Complete to save user:{}]",savedUser.getUserName());
            return;
        }
        
        //sample1을 조회 시, sample1458, sample1, sample1#2 전부 조회된다. sample1458 등을 필터링
        List<User> users = filterMatchingUserNames(userSaveRequestDto.getUserName(), userEntityList);

        //#뒤에 붙일 숫자를 구하는 메서드
        int attachNumber = getAttachNumber(userSaveRequestDto.getUserName(), users);

        userSaveRequestDto.setUserName(userSaveRequestDto.getUserName()+"#"+ attachNumber);
        User savedUser = userRepository.save(new User(userSaveRequestDto));
        log.debug("[Complete to save User:{}]",savedUser.getUserName());

    }

    private int getAttachNumber(String baseName, List<User> users) {
        List<Integer> numberList = users.stream().map(user -> user.getUserName()
                        .substring(baseName.length() + 1))
                .filter(suffix -> suffix.matches("\\d+")) //숫자인지 확인
                .map(Integer::parseInt)
                .sorted()
                .toList();

        return numberList.get(numberList.size()-1) + 1;
    }

    private List<User> filterMatchingUserNames(String dtoUserName, List<User> userEntityList) {
        log.debug("[dto user name:{}]",dtoUserName);
        return userEntityList.stream()
                .filter(user -> user.getUserName().equals(dtoUserName)
                        || user.getUserName().startsWith(dtoUserName + "#")).toList();
    }

}
