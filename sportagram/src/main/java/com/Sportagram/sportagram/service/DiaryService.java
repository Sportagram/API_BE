package com.Sportagram.sportagram.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Sportagram.sportagram.dto.DiaryRequest;
import com.Sportagram.sportagram.entity.Diary;
import com.Sportagram.sportagram.entity.Schedules;
import com.Sportagram.sportagram.entity.Score;
import com.Sportagram.sportagram.entity.Users;
import com.Sportagram.sportagram.repository.DiaryRepository;
import com.Sportagram.sportagram.repository.ScheduleRepository;
import com.Sportagram.sportagram.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScoreCrawlingService scoreCrawlingService;
    @Autowired
    private RatesService ratesService;
    @Autowired
    private CompatService compatService;
    @Autowired
    private ScoreService scoreService;

    public DiaryService(DiaryRepository diaryRepository, ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    public boolean checkIfDiaryExists(String diaryID) {
        // 해당 diaryID가 존재하는지 확인
        Optional<Diary> existingDiary = diaryRepository.findByDiaryID(diaryID);

        // 존재 여부 반환
        return existingDiary.isPresent();
    }


    @Transactional
    public Diary createDiary(DiaryRequest diaryRequest) {
        Diary diary = new Diary();

        String userID = diaryRequest.getUserID();
        diary.setUserID(userID);
        String scheID = diaryRequest.getYear()+"-"+diaryRequest.getMonth()+"-"+diaryRequest.getDay()+"-"+diaryRequest.getStadium();
        diary.setScheduleID(scheID);
        diary.setComments(diaryRequest.getComment());

        Schedules schedule = scheduleRepository
                .findByScheduleID(scheID);

        int oppScore = schedule.getOppScore();
        int teamScore = schedule.getTeamScore();
        String teamID = String.valueOf(schedule.getTeam());
        String oppID = String.valueOf(schedule.getOpponent());

        Users user = userRepository
                .findByUserID(userID);

        String user_team = user.getMyTeam();
        String gameRes = "";
        if (oppID.equals(user_team)) {
            if (oppScore > teamScore) {
                gameRes = "승";
            } else if (oppScore == teamScore) {
                gameRes = "무";
            } else {
                gameRes = "패";
            }
        } else if (teamID.equals(user_team)) {
            if (oppScore > teamScore) {
                gameRes = "패";
            } else if (oppScore == teamScore) {
                gameRes = "무";
            } else {
                gameRes = "승";
            }
        } diary.setGameResult(gameRes);

        String diaryID = userID+"-diary-"+scheID;

        scoreCrawlingService.performCrawling(scheID);
        ratesService.calculateRate(userID);

        Optional<Score> record = scoreService.getRecordByRecordID("R-"+scheID);

        // 다이어리가 존재하면 업데이트 -> matchCnt 세팅하지 않음
        if (!checkIfDiaryExists(diaryID)) {
            diary.setMatchCnt(getDiaryCountByUserID(userID) + 1);
            if (oppID.equals(user_team)) {
                compatService.calculateCompatibility(userID, record.get().getAwayPitchers(), gameRes);
            } else if (teamID.equals(user_team)) {
                compatService.calculateCompatibility(userID, record.get().getHomePitchers(), gameRes);
            }
        } else {
            diary.setMatchCnt(getDiaryCountByUserID(userID));
            System.out.println("이미 해당 다이어리가 존재함");}

        diary.setDiaryID(diaryID);
        return diaryRepository.save(diary);
    }

    public int getDiaryCountByUserID(String userID) {
        return diaryRepository.countByUserID(userID);
    }

    public List<Diary> getDiariesForUser(String userID) {
        // 유저 ID로 일지 조회
        List<Diary> diaries = diaryRepository.findDiariesByUserID(userID);

        // 조회된 스케줄을 콘솔에 출력 (test code)
        if (diaries.isEmpty()) {
            System.out.println("No diaries found for User ID: " + userID);
        } else {
            diaries.forEach(diary -> System.out.println("Diary found: " + diary.getDiaryID()));
        }

        return diaries;
    }

    public List<Diary> getDiariesForUserAndScheID(String userID, String scheduleID) {
        // 유저 ID, 스케줄 ID로 일지 조회
        List<Diary> diaries = diaryRepository.findDiaryByUserIDAndScheduleID(userID, scheduleID);

        // 조회된 스케줄을 콘솔에 출력 (test code)
        if (diaries.isEmpty()) {
            System.out.println("No diaries found for User ID: " + userID + " and schedule ID: " + scheduleID);
        } else {
            diaries.forEach(diary -> System.out.println("Diary found: " + diary.getDiaryID()));
        }

        return diaries;
    }
}
