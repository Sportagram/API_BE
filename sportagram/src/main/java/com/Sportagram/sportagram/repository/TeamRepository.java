package com.Sportagram.sportagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Sportagram.sportagram.entity.Team;


@Repository
public interface TeamRepository extends JpaRepository<Team, String> {

    // shortName을 기준으로 teamID를 찾는 쿼리
    @Query("SELECT t.teamID FROM Team t WHERE t.shortName = :shortName")
    String findTeamIDByShortName(@Param("shortName") String shortName);

    // @Query("SELECT t FROM Team t")
    // List<Team> findAllTeams();
}