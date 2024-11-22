package com.Sportagram.sportagram.controller;

import com.Sportagram.sportagram.entity.News;
import com.Sportagram.sportagram.entity.Users;
import com.Sportagram.sportagram.repository.NewsRepository;
import com.Sportagram.sportagram.repository.UserRepository;
import com.Sportagram.sportagram.service.TeamNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class TeamNewsController {
    private final NewsRepository teamNewsRepository;
    private final TeamNewsService teamNewsService;
    private final UserRepository userRepository;

    @Autowired
    public TeamNewsController(NewsRepository teamNewsRepository,
                              TeamNewsService teamNewsService,
                              UserRepository userRepository) {
        this.teamNewsRepository = teamNewsRepository;
        this.teamNewsService = teamNewsService;
        this.userRepository = userRepository;
    }

    // 구글 로그인을 한 유저에 맞추어 뉴스 조회를 위한 코드

    @GetMapping("/news/myteam")
    public ResponseEntity<?> getMyTeamNews(Authentication authentication) {
        try {
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("로그인이 필요합니다.");
            }

            // OAuth2User에서 이메일 가져오기
            if (authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                String email = oauth2User.getAttribute("email");

                if (email == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("이메일 정보를 찾을 수 없습니다.");
                }

                System.out.println("User email:" + email);

                // 이메일로 사용자 찾기
                Optional<Users> userOptional = userRepository.findByEmail(email);
                if (!userOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("사용자를 찾을 수 없습니다. (email: " + email + ")");
                }

                Users user = userOptional.get();
                String myTeam = user.getMyTeam();
                if (myTeam == null || myTeam.isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body("MyTeam이 설정되지 않았습니다.");
                }
                
                // myTeam == teamName인 뉴스 리스트
                List<News> newsList = teamNewsRepository
                        .findByTeamNameOrderByNewsDateDesc(myTeam);

                return ResponseEntity.ok(newsList);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("잘못된 인증 타입입니다.");
            }
        } catch (Exception e) {
            System.out.println("Error fetching news" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching news: " + e.getMessage());
        }
    }



    // 수동으로 크롤링 시작
    @PostMapping("/news/crawl")
    public ResponseEntity<?> startCrawling() {
        teamNewsService.crawlAllTeamNews();
        return ResponseEntity.ok().build();
    }

    // 팀별 뉴스 조회 test
    @GetMapping("/news/{teamName}")
    public ResponseEntity<List<News>> getNewsByTeam(@PathVariable String teamName) {
        List<News> newsList = teamNewsRepository.findByTeamNameOrderByNewsDateDesc(teamName);
        return ResponseEntity.ok(newsList);
    }
}