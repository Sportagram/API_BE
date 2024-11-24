package com.Sportagram.sportagram.service;

import com.Sportagram.sportagram.dto.UserDto;
import com.Sportagram.sportagram.entity.Users;
import com.Sportagram.sportagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long save(UserDto useDto) {
        Users user = Users.builder().userName(useDto.userName()).email(useDto.email()).nickName(useDto.nickName()).myTeam(useDto.myTeam()).build();
        return userRepository.save(user).getUserID();
    }
}
