package com.Sportagram.sportagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sportagram.sportagram.entity.Schedules;
import com.Sportagram.sportagram.entity.Users;
import com.Sportagram.sportagram.repository.ScheduleRepository;
import com.Sportagram.sportagram.repository.UserRepository;

import java.util.List;

@Service
public class SchedulesService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    // 스케줄을 저장하는 메서드
    public void saveSchedule(Schedules schedule) {
        scheduleRepository.save(schedule);  // Schedules 엔티티 저장
    }


    public List<Schedules> getSchedulesForUser(String userID) {
        // 유저 정보 가져오기
        Users user = userRepository
                .findById(userID)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 유저의 마이팀 ID로 스케줄 조회
        String myTeamID = user.getMyTeam();
        System.out.println("User ID: " + userID + ", My Team ID: " + myTeamID); // test code

        // return scheduleRepository.findSchedulesByTeam(myTeamID);

        // 마이팀 ID로 스케줄 조회
        List<Schedules> schedules = scheduleRepository.findSchedulesByTeam(myTeamID);

        // 조회된 스케줄을 콘솔에 출력 (test code)
        if (schedules.isEmpty()) {
            System.out.println("No schedules found for My Team ID: " + myTeamID);
        } else {
            schedules.forEach(schedule -> System.out.println("Schedule found: " + schedule.getScheduleID())); // 스케줄 아이디 출력으로 수정
        }

        return schedules;
    }
}
