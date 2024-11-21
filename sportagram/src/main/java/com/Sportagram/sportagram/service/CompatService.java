package com.Sportagram.sportagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Sportagram.sportagram.entity.Compatibility;
import com.Sportagram.sportagram.repository.CompatRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CompatService {
    @Autowired
    private CompatRepository compatRepository;

    public CompatService(CompatRepository compatRepository) {
        this.compatRepository = compatRepository;
    }

    public void calculateCompatibility(String userID, String pitcher, String gameRes) {
        System.out.println("calculate compatibility -------------");
        System.out.println("userID: " + userID);
        System.out.println("pitcher: " + pitcher);
        System.out.println("---------------------------------");

        String pitcherName = pitcher.split(";")[0];

        Optional<Compatibility> optionalPitcher = compatRepository.findByPlayerNameAndUserID(pitcherName, userID);

        int win_cnt = 0;
        int lose_cnt = 0;
        int draw_cnt = 0;
        int match_cnt = 1;

        if (gameRes.equals("승")) {
            win_cnt += 1;
        } else if (gameRes.equals("무")) {
            draw_cnt += 1;
        } else if (gameRes.equals("패")) {
            lose_cnt += 1;
        }

        if (optionalPitcher.isPresent()) {
            Compatibility compat = optionalPitcher.get();
            win_cnt += compat.getWinCnt();
            draw_cnt += compat.getDrawCnt();
            lose_cnt += compat.getLossCnt();
            match_cnt += compat.getMatchCnt();
            compat.setWinCnt(win_cnt);
            compat.setDrawCnt(draw_cnt);
            compat.setLossCnt(lose_cnt);
            compat.setMatchCnt(match_cnt);
            compat.setWinRates((float) (win_cnt/match_cnt*100));
            compat.setLossRates((float) (lose_cnt/match_cnt*100));
            compat.setDrawRates((float) (draw_cnt/match_cnt*100));
            compatRepository.save(compat);
        }
        else {
            Compatibility compat = new Compatibility();
            compat.setCompatID(userID+"-compat-"+pitcherName);
            compat.setUserID(userID);
            compat.setPlayerName(pitcherName);
            compat.setWinCnt(win_cnt);
            compat.setDrawCnt(draw_cnt);
            compat.setLossCnt(lose_cnt);
            compat.setMatchCnt(match_cnt);
            compat.setWinRates((float) (win_cnt/match_cnt*100));
            compat.setLossRates((float) (lose_cnt/match_cnt*100));
            compat.setDrawRates((float) (draw_cnt/match_cnt*100));
            compatRepository.save(compat);
        }
    }

    public List<Compatibility> getCompatibilityDataByUserID(String userID) {
        return compatRepository.findByUserID(userID);
    }

    public Compatibility getHighestWinRateByUserID(String userID) {
        List<Compatibility> compatibilityData = compatRepository.findByUserID(userID);

        // 리스트가 비어 있지 않으면, win_rates가 가장 높은 객체를 찾음
        if (compatibilityData != null && !compatibilityData.isEmpty()) {
            return compatibilityData.stream()
                    .max(Comparator.comparingDouble(Compatibility::getWinRates))
                    .orElse(null); // 가장 높은 win_rates 값을 가진 Compatibility 객체 반환
        }
        return null; // 데이터가 없으면 null 반환
    }

    public Compatibility getHighestLoseRateByUserID(String userID) {
        List<Compatibility> compatibilityData = compatRepository.findByUserID(userID);

        // 리스트가 비어 있지 않으면, lose_rates가 가장 높은 객체를 찾음
        if (compatibilityData != null && !compatibilityData.isEmpty()) {
            return compatibilityData.stream()
                    .max(Comparator.comparingDouble(Compatibility::getLossRates))
                    .orElse(null); // 가장 높은 lose_rates 값을 가진 Compatibility 객체 반환
        }
        return null; // 데이터가 없으면 null 반환
    }

}
