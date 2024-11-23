package com.Sportagram.sportagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Sportagram.sportagram.entity.Score;
import com.Sportagram.sportagram.repository.ScoreRepository;

import java.util.Optional;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    public Optional<Score> getRecordByRecordID(String recordID) {
        return scoreRepository.findByRecordID(recordID);
    }

    // 스코어를 저장하는 메서드
    public void saveScore(Score score) {
        scoreRepository.save(score);  // Score 엔티티 저장
    }
}
