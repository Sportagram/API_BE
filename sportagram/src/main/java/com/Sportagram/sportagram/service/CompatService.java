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
        System.out.println("gameRes: " + gameRes);
        System.out.println("---------------------------------");

        // sp_loss (선발투수 승) / sp_wins (선발투수 패) / out_count (이닝 계산용) /
        // homeruns (피홈런) / hits (피안타) / bb (4사구) / k (탈삼진) / runs (실점) / er (자책점)
        // 선수명; 등판; 결과; 승; 패; 세; 이닝; 타자; 투구수; 타수; 피안타; 홈런; 4사구; 삼진; 실점; 자책; 평균자책점;
        String pitcherName = pitcher.split(";")[0];
        String spRes = pitcher.split(";")[2];
        String outs = pitcher.split(";")[6];
        int hits = Integer.parseInt(pitcher.split(";")[10]);
        int homeruns = Integer.parseInt(pitcher.split(";")[11]);
        int bb = Integer.parseInt(pitcher.split(";")[12]);
        int k = Integer.parseInt(pitcher.split(";")[13]);
        int runs = Integer.parseInt(pitcher.split(";")[14]);
        int er = Integer.parseInt(pitcher.split(";")[15]);

        int outcounts = 0;
        String[] temp = outs.split(" ");
        outcounts += Integer.parseInt(temp[0]) * 3;
        temp = temp[1].split("/");
        outcounts += Integer.parseInt(temp[0]);

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

        Compatibility compat;

        if (optionalPitcher.isPresent()) {
            compat = optionalPitcher.get();
            win_cnt += compat.getWinCnt();
            draw_cnt += compat.getDrawCnt();
            lose_cnt += compat.getLossCnt();
            match_cnt += compat.getMatchCnt();

            if (spRes.equals("승")) {
                int res = compat.getSPwins();
                compat.setSPwins(res + 1);
            } else if (spRes.equals("패")) {
                int res = compat.getSPloss();
                compat.setSPloss(res + 1);
            }

            outcounts += compat.getOutCount();
            hits += compat.getHits();
            homeruns += compat.getHomeruns();
            bb += compat.getBb();
            k += compat.getK();
            runs += compat.getRuns();
            er += compat.getEr();
        }
        else {
            compat = new Compatibility();
            compat.setCompatID(userID+"-compat-"+pitcherName);
            compat.setUserID(userID);
            compat.setPlayerName(pitcherName);

            if (spRes.equals("승")) {
                compat.setSPwins(1);
            } else if (spRes.equals("패")) {
                compat.setSPloss(1);
            }
        }

        compat.setWinCnt(win_cnt);
        compat.setDrawCnt(draw_cnt);
        compat.setLossCnt(lose_cnt);
        compat.setMatchCnt(match_cnt);
        compat.setWinRates((float) (win_cnt/match_cnt*100));
        compat.setLossRates((float) (lose_cnt/match_cnt*100));
        compat.setDrawRates((float) (draw_cnt/match_cnt*100));

        compat.setOutCount(outcounts);
        compat.setHits(hits);
        compat.setHomeruns(homeruns);
        compat.setBb(bb);
        compat.setK(k);
        compat.setRuns(runs);
        compat.setEr(er);

        compatRepository.save(compat);
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
