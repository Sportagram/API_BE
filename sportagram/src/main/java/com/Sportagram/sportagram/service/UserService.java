package com.Sportagram.sportagram.service;

import com.Sportagram.sportagram.entity.Users;
import com.Sportagram.sportagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users findUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    public Users saveUser(Users user) {
        return userRepository.save(user);
    }
}