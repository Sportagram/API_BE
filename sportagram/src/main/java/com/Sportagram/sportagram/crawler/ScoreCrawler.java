package com.Sportagram.sportagram.crawler;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Sportagram.sportagram.entity.Score;
import com.Sportagram.sportagram.service.ScoreService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScoreCrawler {
    @Autowired
    private ScoreService scoreService;

    public void scoreCrawl(String scheduleID) {
        // ChromeDriver 설정 - 크롬 드라이버 경로
        System.setProperty("webdriver.chrome.driver", "src/main/resources/static/driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.koreabaseball.com/Schedule/GameCenter/Main.aspx");

            Score new_record = new Score();

            // temp
            String targetYear;
            // String targetSeries = "0,9,6";
            String targetMonth;
            String targetDay;
            String targetTime;
            // String targetPlay = "한화2vs8LG";
            String targetStadium;
            // 결과 검색: 시즌, 월, 일, 경기장 (현재)
            // ** 더블헤더 경기가 있을 수 있음

            String[] scheID = scheduleID.split("-");
            targetYear = scheID[0];
            targetMonth = scheID[1];
            targetDay = scheID[2];
            targetStadium = scheID[3];
            targetTime = scheID[4].split(":")[0] + ":" + scheID[4].split(":")[1];

            // ex) 2024-03-23-잠실-14:00:00
            new_record.setScheduleID(scheduleID);

            WebElement calButton = driver.findElement(By.className("ui-datepicker-trigger"));
            calButton.click();

            Select selectYear = new Select(driver.findElement(By.className("ui-datepicker-year")));
            selectYear.selectByValue(targetYear);

            Select selectMonth = new Select(driver.findElement(By.className("ui-datepicker-month")));
            selectMonth.selectByValue(String.valueOf(Integer.parseInt(targetMonth)-1)); // 1월 == 0

            WebElement selectDay = driver.findElement(By.xpath("//a[text()='" + targetDay + "']"));
            selectDay.click();

            // (temp) "잠실" 경기장을 포함하는 게임 요소를 찾기 (해당 경기장 텍스트를 갖는 클래스 이름을 찾음 -> 클릭)
            WebElement gameElement1 = driver.findElement(By.xpath("//li[contains(@s_nm, '" + targetStadium + "')]"));
            String className = gameElement1.getAttribute("class");
            WebElement gameElement2 = driver.findElement(By.xpath("//li[contains(@s_nm, '" + targetStadium + "') and contains(@class, '" + className + "')]"));
            gameElement2.click();

            // 리뷰 섹션으로 이동
            WebElement reviewTab = driver.findElement(By.xpath("//li[@class='tab-tit' and @section='REVIEW']"));
            reviewTab.click();
            
            // 이닝 당 스코어
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 안 읽어지는 오류 발생하여 수정 (대기)
            WebElement tbody = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#tblScordboard2 tbody")));
            List<String> scoreData = new ArrayList<String>();
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));
            for (WebElement row : rows) {
                List<WebElement> cols = row.findElements(By.tagName("td"));
                for (WebElement col : cols) {
                    System.out.print(col.getText() + "\t"); // 각 셀의 값을 출력
                }
                System.out.println();
                scoreData.add(row.getText());
            }
            new_record.setAwayScore(scoreData.get(0));
            new_record.setHomeScore(scoreData.get(1));
            
            // 최종 스코어 파트
            WebElement table3 = driver.findElement(By.cssSelector("#tblScordboard3 tbody"));
            List<String> recordsData = new ArrayList<>();
            List<WebElement> rows3 = table3.findElements(By.tagName("tr"));
            for (WebElement row : rows3) {
                // 각 행에서 정보 추출
                List<WebElement> cols = row.findElements(By.tagName("td"));
                if (!cols.isEmpty()) {
                    String runs = cols.get(0).getText(); // R (득점)
                    String hits = cols.get(1).getText(); // H (안타)
                    String errors = cols.get(2).getText(); // E (실책)
                    String walks = cols.get(3).getText(); // B (볼넷)
                    String records = "R: " + runs + ", H: " + hits + ", E: " + errors + ", B: " + walks;
                    System.out.println(records);
                    recordsData.add(records);
                }
            }
            System.out.println();
            new_record.setAwayRecords(recordsData.get(0));
            new_record.setHomeRecords(recordsData.get(1));

            // 세부 기록 파트
            WebElement summary = driver.findElement(By.cssSelector("#tblEtc tbody"));
            // tbody 내부의 모든 행(tr) 찾기
            List<WebElement> rows4 = summary.findElements(By.tagName("tr"));

            System.out.println("Detail Records");
            List<String> gameData = new ArrayList<>();
            for (WebElement row : rows4) {
                // tr에서 텍스트 전체를 가져오기 (구분X)
                String[] parts = row.getText().split(" ", 2); // 기록 한 줄 -> tag: players ~
                String temp = parts[0] + ": " + parts[1];
                System.out.println(temp);
                gameData.add(temp);
            }
            String detailRec = String.join(";", gameData);
            new_record.setGameRecords(detailRec);
            System.out.println();

            // 양팀 투수 기록 -> 원래 전체 기록 다 읽어왔었는데, 너무 크다고 DB에 저장이 안 됨. 선발 투수만 저장하는 것으로 수정.
            // 원정 팀 투수 기록
            WebElement awayPitchers = driver.findElement(By.cssSelector("#tblAwayPitcher tbody"));
            List<WebElement> rows5 = awayPitchers.findElements(By.tagName("tr"));
            String awaySP = "";
            // for (WebElement row : rows5)
            for (int i = 0; i < rows5.size(); i++) {
                List<WebElement> cols = rows5.get(i).findElements(By.tagName("td"));
                for (WebElement col : cols) {
                    if (i == 0) awaySP += col.getText() + ";";
                    System.out.print(col.getText() + "\t");
                }
                System.out.println();
            }
            new_record.setAwayPitchers(awaySP);
            System.out.println();

            // 홈 팀 투수 기록
            WebElement homePitchers = driver.findElement(By.cssSelector("#tblHomePitcher tbody"));
            List<WebElement> rows6 = homePitchers.findElements(By.tagName("tr"));
            String homeSP = "";
            for (int i = 0; i < rows6.size(); i++) {
                List<WebElement> cols = rows6.get(i).findElements(By.tagName("td"));
                for (WebElement col : cols) {
                    if (i == 0) homeSP += col.getText() + ";";
                    System.out.print(col.getText() + "\t");
                }
                System.out.println();
            }
            new_record.setHomePitchers(homeSP);
            System.out.println();

            new_record.setRecordID("R-"+scheduleID);
            scoreService.saveScore(new_record);


            // test code (크롤링 페이지 확인용)
            try {
                Thread.sleep(15000); // 15초 동안 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit(); // 웹 드라이버 종료
        }
    }
}

