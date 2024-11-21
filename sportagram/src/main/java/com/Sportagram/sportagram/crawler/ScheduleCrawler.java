package com.Sportagram.sportagram.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.Sportagram.sportagram.entity.Schedules;
import com.Sportagram.sportagram.repository.ScheduleRepository;
import com.Sportagram.sportagram.repository.TeamRepository;
import com.Sportagram.sportagram.service.SchedulesService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ScheduleCrawler {

    private TeamRepository teamRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleCrawler(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Autowired
    private SchedulesService schedulesService;

    @Scheduled(cron = "0 0 20-24,0-1 * * ?")
    public void scheduleCrawl() {
        // ChromeDriver 설정 - 크롬 드라이버 경로
        System.setProperty("webdriver.chrome.driver", "src/main/resources/static/driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.koreabaseball.com/Schedule/Schedule.aspx");

            String year = "2024";
            Select selectYear = new Select(driver.findElement(By.id("ddlYear")));
            selectYear.selectByValue(year); // 현재 연도가 기본이므로, 실제 시즌 중에는 설정 필요 X (or 지정해두고 변경 필요 X)

            Select selectLeague = new Select(driver.findElement(By.id("ddlSeries")));
            selectLeague.selectByValue("0,9,6"); // 정규 시즌 선택

            int month; // 2024 기준
            String formattedMonth;
            // month의 경우, 처음에 전체 일정 저장할 때만 전체 돌리면 되고, 이외에는 현재 month(기본)만 하면 됨 (스코어 업데이트용)
            for (month = 3; month <= 10; month++) {
                formattedMonth = String.format("%02d", month);
                Select selectMonth = new Select(driver.findElement(By.id("ddlMonth")));
                selectMonth.selectByValue(formattedMonth); // month 선택

                // tbody 안의 모든 tr 가져오기
                List<WebElement> rows = driver.findElements(By.cssSelector("#tblScheduleList > tbody > tr"));

                String textValue;

                // 팀1스코어1vs스코어2팀2 -> 파싱 ** 우취 등으로 취소된 경기의 경우 매칭 불가 -> 수정 완료 / 두 가지 경우로 매칭
                Pattern pattern = Pattern.compile("(\\D+)(\\d+)vs(\\d+)(\\D+)");
                Pattern patternWithoutScore = Pattern.compile("(\\D+)vs(\\D+)");
                Matcher matcher;
                Matcher matcherWithoutScore;

                Schedules schedules1;
                Schedules schedules2;
                Date matchDate = null;
                Time matchTime = null;
                String team1 = null;
                String team2 = null;
                String stadium = null;

                // 모든 tr 반복 읽기
                for (WebElement row : rows) {
                    // td 찾기
                    List<WebElement> cells = row.findElements(By.tagName("td"));

                    schedules1 = new Schedules();

                    // for (WebElement cell : cells)
                    for (int i = 0; i < cells.size(); i++) {

                        textValue = cells.get(i).getText();
                        if (textValue.contains("(")) {
                            System.out.println(textValue);
                            matchDate = Date.valueOf(year + "-" + textValue.substring(0, 2) + "-" + textValue.substring(3, 5));
                        } else if (textValue.contains(":")) {
                            matchTime = Time.valueOf(textValue + ":00");
                            schedules1.setMatchTime(matchTime);
                        } else if (textValue.contains("vs")) {
                            matcher = pattern.matcher(textValue);
                            matcherWithoutScore = patternWithoutScore.matcher(textValue);

                            if (matcher.matches()) {
                                team1 = matcher.group(1);
                                int score1 = Integer.parseInt(matcher.group(2));
                                int score2 = Integer.parseInt(matcher.group(3));
                                team2 = matcher.group(4);

                                String team1ID = teamRepository.findTeamIDByShortName(team1);
                                String team2ID = teamRepository.findTeamIDByShortName(team2);
                                System.out.println(team1);
                                System.out.println(team2);
                                System.out.println(team1ID);
                                System.out.println(team2ID);

                                /*
                                schedules1.setTeam(team1ID);
                                schedules1.setOpponent(team2ID);
                                schedules1.setTeamScore(score1);
                                schedules1.setOppScore(score2);

                                 */

                                // 기존 데이터 날리고 새로 넣어야 됨 **
                                // 원정 vs 홈 순서로 되어있기 때문에 team2가 홈팀이 됨, 수정.
                                schedules1.setOpponent(team1ID);
                                schedules1.setTeam(team2ID);
                                schedules1.setOppScore(score1);
                                schedules1.setTeamScore(score2);

                                System.out.print(team1 + "\t" + score1 + "\tvs\t" + score2 + "\t" + team2 + "\t");
                            } else if (matcherWithoutScore.matches()) {
                                team1 = matcherWithoutScore.group(1);
                                team2 = matcherWithoutScore.group(2);

                                String team1ID = teamRepository.findTeamIDByShortName(team1);
                                String team2ID = teamRepository.findTeamIDByShortName(team2);

                                schedules1.setTeam(team1ID);
                                schedules1.setOpponent(team2ID);
                                schedules1.setTeamScore(0);
                                schedules1.setOppScore(0);

                                System.out.print(team1 + "\tvs\t" + team2 + "\t");
                            } else {
                                System.out.print(textValue + "\t"); // 매칭 불가 케이스 (오류)
                            }
                        } else if (textValue.equals("리뷰") || textValue.isEmpty()) { // 우취된 경기의 경우 "리뷰"로 필터링 불가 -> 리뷰칸이 비었는지 체크
                            i = cells.size() - 3;
                        } else if (i == cells.size()-1) {
                            schedules1.setMatchStatus(textValue);

                            System.out.print(textValue + "\t");
                        } else {
                            schedules1.setStadium(textValue);

                            stadium = textValue;

                            System.out.print(textValue + "\t"); // \t로 구분
                        }
                    }
                    // matchDate 에러 뜨는 거 해결하기. =====> 완료
                    // matchDate 이용해서 scheduleID 생성하기. =====> 완료
                    schedules1.setMatchDate(matchDate);
                    schedules1.setScheduleID(matchDate + "-" + stadium + "-" + matchTime);

                    schedulesService.saveSchedule(schedules1);
                    System.out.println();

                    // 날짜
                    // 시간    팀1    점수1    vs    점수2    팀2    구장    경기상태
                    // ...
                    // matchDate
                    // matchTime    teamID  teamScore   oppID   oppScore    stadium matchStatus

                    // 팀, 스케줄 등의 아이디 자동으로 생성해주는 메소드 =====> 팀 정보의 경우 DB에 넣고 시작
                    // matchDate에 값 하나로 같은 row에 있는 값 다 넣어줘야 됨
                    // 스케줄 테이블에 matchTime 추가 =====> 완료 - date / time 으로 분리
                    // 스케줄 테이블에 teamScore, oppScore 추가 =====> 완료
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit(); // 웹 드라이버 종료
        }
    }
}
