package com.Sportagram.sportagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Sportagram.sportagram.entity.Rate;


public interface RatesRepository extends JpaRepository<Rate, Integer> {
    Rate findByUserID(String userID);
}
