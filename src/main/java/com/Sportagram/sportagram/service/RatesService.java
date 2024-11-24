package com.Sportagram.sportagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sportagram.sportagram.entity.Diary;
import com.Sportagram.sportagram.entity.Rate;
import com.Sportagram.sportagram.repository.DiaryRepository;
import com.Sportagram.sportagram.repository.RatesRepository;

import java.util.List;

@Service
public class RatesService {
    @Autowired
    private RatesRepository ratesRepository;
    @Autowired
    private DiaryRepository diaryRepository;

    public RatesService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public Rate calculateRate(Long userID) {
        List<Diary> diaries = diaryRepository.findDiariesByUserID(userID);

        int totalMatches = 1; // 총 매치 수
        int winCount = 0;      // 승리한 매치 수
        int drawCount = 0;     // 무승부 매치 수
        int loseCount = 0;     // 패배한 매치 수

        // 다이어리 항목들에서 승/패/무 매치 계산
        for (Diary diary : diaries) {
            int matchCnt = diary.getMatchCnt();
            if (totalMatches < matchCnt) totalMatches = matchCnt;

            // 첫 실행에서 세팅이 안 됨
            switch (diary.getGameResult()) {
                case "승":
                    winCount += 1;
                    break;
                case "무":
                    drawCount += 1;
                    break;
                case "패":
                    loseCount += 1;
                    break;
            }
        }

        float winRate = winCount / (float) totalMatches * 100;
        float drawRate = drawCount / (float) totalMatches * 100;
        float loseRate = loseCount / (float) totalMatches * 100;

        // test code
        System.out.println("\nTEST CODE -------------------");
        System.out.println("UserID: " + userID);
        System.out.println("Total Matches: " + totalMatches);
        System.out.println("Wins: " + winCount);
        System.out.println("Losses: " + loseCount);
        System.out.println("Draws: " + drawCount);
        System.out.println("Win Rate: " + winRate);
        System.out.println("Loss Rate: " + loseRate);
        System.out.println("Draw Rate: " + drawRate);
        System.out.println("------------------------------\n");


        Rate rate = new Rate();
        rate.setRatesID(userID+"-rates");
        rate.setUserID(String.valueOf(userID));
        rate.setMatchCnt(totalMatches);
        rate.setWins(winCount);
        rate.setLoss(loseCount);
        rate.setDraw(drawCount);
        rate.setWinRates(winRate);
        rate.setLossRates(loseRate);
        rate.setDrawRates(drawRate);

        return ratesRepository.save(rate);
        
        // 11/17 일지 생성 한 번만에 rates 업데이트가 안 됨
        // 같은 경기를 두 번은 등록해야 카운트가 됨
    }

    public Rate getRateByUserID(String userID) {
        // 유저 ID로 Rate 객체를 조회
        Rate rate = ratesRepository.findByUserID(userID);

        if (rate == null) {
            System.out.println("User not found with ID: " + userID);
        }

        return rate;
    }
}
