package com.Sportagram.sportagram.controller;

import com.Sportagram.sportagram.entity.Team;
import com.Sportagram.sportagram.repository.TeamRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
public class TeamController {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping("/teams")
    public List<Team> getAllTeams() {
        return teamRepository.findAll();  // 모든 팀 정보를 반환
    }
//    public List<Team> getTeams(HttpServletResponse response) {
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//        return teamRepository.findAll();
//    }
}