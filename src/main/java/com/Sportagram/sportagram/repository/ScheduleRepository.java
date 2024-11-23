package com.Sportagram.sportagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.Sportagram.sportagram.entity.Schedules;


import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedules, String> {
    Schedules findByScheduleID(String scheID);

    @Query("SELECT s FROM Schedules s WHERE s.team = :myTeam OR s.opponent = :myTeam")
    List<Schedules> findSchedulesByTeam(@Param("myTeam") String myTeam);

    // 11/13
    // 유저의 마이팀 아이디 조회까지는 됨.
    // 마이팀 아이디로 팀 스케줄 조회가 안됨.
    // 에러X, 그냥 없다고 뜸.

    // 11/16
    // 수정 완료.
    // Schedules 엔티티에서 외래 키 매핑 어노테이션 지우면서 joincol만 남겨뒀던 거 그냥 col로 수정함.
}
