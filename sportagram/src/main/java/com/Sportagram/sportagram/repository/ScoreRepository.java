package com.Sportagram.sportagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Sportagram.sportagram.entity.Score;


import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, String> {
    Optional<Score> findByRecordID(String recordID);
}
