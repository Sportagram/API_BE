package com.Sportagram.sportagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Sportagram.sportagram.entity.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {
    Users findByUserName(String userName);

    Users findByUserID(String userID);

    // Optional<Users> findByEmail(String email);
    boolean existsByNickName(String nickName);
}
