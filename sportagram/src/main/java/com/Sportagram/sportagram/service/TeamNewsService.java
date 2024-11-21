package com.Sportagram.sportagram.service;

import com.Sportagram.sportagram.entity.News;
import com.Sportagram.sportagram.repository.NewsRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@EnableScheduling
public class TeamNewsService {

    private final NewsRepository teamNewsRepository;
    private static final Logger logger = LoggerFactory.getLogger(TeamNewsService.class);

    @Autowired
    public TeamNewsService(NewsRepository teamNewsRepository) {
        this.teamNewsRepository = teamNewsRepository;
    }

    // 수동 크롤링
    // 호출하여 뉴스를 크롤링합니다.
    public void crawlAllTeamNews() {
        startNews();
    }

    public void startNews() {
        System.out.println("start news");
        // 완료
        DoosanBearsNews();
        SamsungLionsNews();
        HanwhaEaglesNews();
        KiwoomNews();
        NCDinosNews();
        LotteGiantNews();

        // test 중
        // ktWizNews();

        /*
        GTwinsNews();
        LandersNews();

        SamsungLionsNews();
        KiaTigersNews();
         */
    }

    // 매일 자정에 시행
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleNewsCrawling() {
        System.out.println("뉴스 크롤링 스케줄러 시작: " + LocalDateTime.now());
        try {
            
            // 구단 뉴스/소식 크롤링
            // 완료
            DoosanBearsNews();
            SamsungLionsNews();
            HanwhaEaglesNews();
            KiwoomNews();
            NCDinosNews();
            LotteGiantNews();
             
            // 현재 test 중
            // ktWizNews();
            
            System.out.println("뉴스 크롤링 완료: " + LocalDateTime.now());
        } catch (Exception e) {
            System.out.println("스케줄된 크롤링 중 오류 발생: " + e.getMessage());
        }
    }

    // 한화이글스 뉴스 크롤링
    public void HanwhaEaglesNews(){
        WebDriver driver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");  // 브라우저 창 없이 실행
            driver = new ChromeDriver(options);

            // 두산 베어스 뉴스 페이지 접속
            driver.get("https://www.hanwhaeagles.co.kr/FA/CN/PCFACN01.do");
            System.out.println("한화이글스 뉴스 페이지 로딩 시작");

            // JavaScript 실행 완료 대기
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.list_notice > ul")));

            // 뉴스 목록 가져오기
            List<WebElement> newsElements = driver.findElements(By.cssSelector("li.em"));
            System.out.println("찾은 뉴스 개수: "+ newsElements.size());

            for (WebElement element : newsElements) {
                News news = new News();
                news.setTeamName("한화");

                try {
                    
                    // 제목 추출 및 저장
                    WebElement titleElement = element.findElement(By.cssSelector("div.col.tit"));
                    if (titleElement == null || titleElement.getText().isEmpty()) {
                        continue;
                    }

                    String title = titleElement.getText();

                    // 중복 체크
                    if (teamNewsRepository.existsByTeamNameAndTitle("한화이글스", title)) {
                        System.out.println("중복된 뉴스 제목: " + title);
                        continue;
                    }
                    news.setTitle(title);

                    // URL 저장
                    WebElement linkElement = element.findElement(By.cssSelector("a.link"));
                    String newsUrl = linkElement.getAttribute("href");
                    if (newsUrl != null && !newsUrl.isEmpty()) {
                        if (!newsUrl.startsWith("http")) {
                            newsUrl = "https://www.hanwhaeagles.co.kr" + newsUrl;
                        }
                        news.setUrl(newsUrl);
                        System.out.println("뉴스 URL: " + newsUrl);
                    }

                    // 작성 날짜 저장
                    WebElement dateElement = element.findElement(By.cssSelector("div.col.date"));
                    if (dateElement != null) {
                        String dateStr = dateElement.getText().trim();
                        try {
                            // 날짜 형식을 yyyy-MM-dd로 변환
                            dateStr = dateStr.replace(".", "-");  // 마침표를 하이픈으로 변환

                            // LocalDateTime을 Instant로 변환
                            Instant newsDate = LocalDateTime.parse(dateStr + " 00:00:00",
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant();

                            news.setNewsDate(newsDate);
                            System.out.println("날짜: " + dateStr);
                        } catch (Exception e) {
                            System.out.println("날짜 파싱 오류: " + dateStr);
                            e.printStackTrace();
                        }
                    }

                    // DB에 저장
                    if (news.getTitle() != null && !news.getTitle().isEmpty()) {
                        News savedNews = teamNewsRepository.save(news);
                        System.out.println("저장된 뉴스 ID: " + savedNews.getId());
                    }
                } catch (Exception e) {
                    System.out.println("뉴스 항목 처리 중 오류: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("크롤링 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("WebDriver 종료");
            }
        }
    }

    // 키움 뉴스 크롤링
    public void KiwoomNews(){
        WebDriver driver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);

            driver.get("https://heroesbaseball.co.kr/story/heroesNews/list.do");
            System.out.println("키움 히어로즈 뉴스 페이지 로딩 시작");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.teamNews")));

            List<WebElement> newsElements = driver.findElements(By.cssSelector("ul.teamNews > li"));
            System.out.println("찾은 뉴스 개수: " + newsElements.size());

            for (WebElement element : newsElements) {
                try {
                    // 제목 추출
                    WebElement titleElement = element.findElement(By.cssSelector("a"));
                    if (titleElement == null || titleElement.getText().isEmpty()) {
                        continue;
                    }

                    String title = titleElement.getText();

                    // 중복 체크
                    if (teamNewsRepository.existsByTeamNameAndTitle("키움", title)) {
                        System.out.println("중복된 뉴스 제목: " + title);
                        continue;
                    }

                    News news = new News();
                    news.setTeamName("키움");
                    news.setTitle(title);

                    // URL 저장
                    String newsUrl = titleElement.getAttribute("href");
                    if (newsUrl != null && !newsUrl.isEmpty()) {
                        if (!newsUrl.startsWith("http")) {
                            newsUrl = "https://heroesbaseball.co.kr" + newsUrl;
                        }
                        news.setUrl(newsUrl);
                        System.out.println("뉴스 URL: " + newsUrl);
                    }

                    // 날짜 추출
                    WebElement dateElement = element.findElement(By.cssSelector("span"));
                    if (dateElement != null) {
                        String dateStr = dateElement.getText().trim();
                        dateStr = dateStr.replace(".", "-");

                        // LocalDateTime을 Instant로 변환
                        Instant newsDate = LocalDateTime.parse(dateStr + " 00:00:00",
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                .atZone(ZoneId.systemDefault())
                                .toInstant();

                        news.setNewsDate(newsDate);
                        System.out.println("날짜: " + dateStr);
                    }

                    // DB에 저장
                    News savedNews = teamNewsRepository.save(news);
                    System.out.println("새로운 뉴스 저장 완료 - 제목: " + title + ", ID: " + savedNews.getId());

                } catch (Exception e) {
                    System.out.println("뉴스 항목 처리 중 오류: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("크롤링 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("WebDriver 종료");
            }
        }
    }

    // nc 다이노스 뉴스 크롤링
    public void NCDinosNews(){
        WebDriver driver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);

            driver.get("https://www.ncdinos.com/dinos/news.do");
            System.out.println("NC 다이노스 뉴스 페이지 로딩 시작");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.board > ul")));

            List<WebElement> newsElements = driver.findElements(By.cssSelector("div.board > ul > li"));
            System.out.println("찾은 뉴스 개수: " + newsElements.size());

            for (WebElement element : newsElements) {
                try {
                    // 제목 추출
                    WebElement titleElement = element.findElement(By.cssSelector("li > a.title"));
                    if (titleElement == null || titleElement.getText().isEmpty()) {
                        continue;
                    }

                    String title = titleElement.getText();

                    // 중복 체크
                    if (teamNewsRepository.existsByTeamNameAndTitle("NC", title)) {
                        System.out.println("중복된 뉴스 제목: " + title);
                        continue;
                    }

                    News news = new News();
                    news.setTeamName("NC");
                    news.setTitle(title);

                    // URL 저장
                    String newsUrl = titleElement.getAttribute("href");
                    if (newsUrl != null && !newsUrl.isEmpty()) {
                        if (!newsUrl.startsWith("http")) {
                            newsUrl = "https://www.ncdinos.com" + newsUrl;
                        }
                        news.setUrl(newsUrl);
                        System.out.println("뉴스 URL: " + newsUrl);
                    }

                    // 날짜 추출 (날짜가 있는 경우)
                    // NC 다이노스는 각 게시물에 들어가야만 작성 일자가 나타나기 때문에 하나하나 읽어오기 어려워
                    // 날짜 추출이 힘듦
                    System.out.println("날짜 정보가 없는 뉴스입니다: " + title);


                    // DB에 저장
                    News savedNews = teamNewsRepository.save(news);
                    System.out.println("새로운 뉴스 저장 완료 - 제목: " + title + ", ID: " + savedNews.getId());

                } catch (Exception e) {
                    System.out.println("뉴스 항목 처리 중 오류: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("크롤링 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("WebDriver 종료");
            }
        }
    }

    // 롯데 자이언츠 뉴스 크롤링
    public void LotteGiantNews(){
        WebDriver driver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);

            driver.get("https://www.giantsclub.com/html/?pcode=783");
            System.out.println("롯데 자이언츠 뉴스 페이지 로딩 시작");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.news-list > ul.news-list")));

            List<WebElement> newsElements = driver.findElements(By.cssSelector("ul.news-list > li"));
            System.out.println("찾은 뉴스 개수: " + newsElements.size());

            for (WebElement element : newsElements) {
                try {
                    // 제목 저장
                    WebElement titleElement = element.findElement(By.cssSelector("div.news-list-cont > a"));
                    if (titleElement == null || titleElement.getText().isEmpty()) {
                        continue;
                    }

                    String title = titleElement.getText();
                    if (title.length() > 255) {  // DB 컬럼 길이에 맞게 조정
                        title = title.substring(0, 255);
                    }

                    // 중복 체크
                    if (teamNewsRepository.existsByTeamNameAndTitle("롯데", title)) {
                        System.out.println("중복된 뉴스 제목: " + title);
                        continue;
                    }

                    News news = new News();
                    news.setTeamName("롯데");
                    news.setTitle(title);

                    // News URL 저장
                    String newsUrl = titleElement.getAttribute("href");
                    if (newsUrl != null && !newsUrl.isEmpty()) {
                        if (!newsUrl.startsWith("http")) {
                            newsUrl = "https://www.giantsclub.com" + newsUrl;
                        }
                        news.setUrl(newsUrl);
                        System.out.println("뉴스 URL: " + newsUrl);
                    }

                    // 날짜 저장
                    WebElement dateElement = element.findElement(By.cssSelector("span.n-date"));
                    if (dateElement != null) {
                        String dateStr = dateElement.getText().trim();
                        try {
                            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                            // LocalDate를 Instant로 변환
                            Instant newsDate = date.atStartOfDay()
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant();

                            news.setNewsDate(newsDate);
                            System.out.println("날짜: " + dateStr);
                        } catch (Exception e) {
                            System.out.println("날짜 파싱 오류: " + dateStr);
                        }
                    }

                    // DB에 저장
                    News savedNews = teamNewsRepository.save(news);
                    System.out.println("새로운 뉴스 저장 완료 - 제목: " + title + ", ID: " + savedNews.getId());

                } catch (Exception e) {
                    System.out.println("뉴스 항목 처리 중 오류: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("크롤링 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("WebDriver 종료");
            }
        }
    }

    // 랜더스 뉴스 크롤링
    // 얘는 어떻게 크롤링 해야 되나....
    public void LandersNews(){

    }

    // kt wiz 뉴스
    public void ktWizNews(){
        WebDriver driver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);

            driver.get("https://www.ssglanders.com/media/news");
            System.out.println("kt wiz 뉴스 페이지 로딩 시작");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.board_list.list_view > ul")));

            List<WebElement> newsElements = driver.findElements(By.cssSelector("ul > li"));
            System.out.println("찾은 뉴스 개수: " + newsElements.size());

            for (WebElement element : newsElements) {
                try {
                    // 제목 추출
                    WebElement titleElement = element.findElement(By.cssSelector("div.tit_detail > div > dl > dt"));
                    if (titleElement == null || titleElement.getText().isEmpty()) {
                        continue;
                    }

                    String title = titleElement.getText();

                    // 중복 체크
                    if (teamNewsRepository.existsByTeamNameAndTitle("SSG", title)) {
                        System.out.println("중복된 뉴스 제목: " + title);
                        continue;
                    }

                    News news = new News();
                    news.setTeamName("KT");
                    news.setTitle(title);

                    // URL 저장
                    String newsUrl = titleElement.getAttribute("href");
                    if (newsUrl != null && !newsUrl.isEmpty()) {
                        if (!newsUrl.startsWith("http")) {
                            newsUrl = "https://www.ssglanders.com" + newsUrl;
                        }
                        news.setUrl(newsUrl);
                        System.out.println("뉴스 URL: " + newsUrl);
                    }

                    // 날짜 저장
                    WebElement dateElement = element.findElement(By.cssSelector("div.tit_info > ul > li > span.date"));
                    if (dateElement != null) {
                        String dateStr = dateElement.getText().trim();
                        try {
                            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                            // LocalDate를 Instant로 변환
                            Instant newsDate = date.atStartOfDay()
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant();

                            news.setNewsDate(newsDate);
                            System.out.println("날짜: " + dateStr);
                        } catch (Exception e) {
                            System.out.println("날짜 파싱 오류: " + dateStr);
                        }
                    }

                    // DB에 저장
                    News savedNews = teamNewsRepository.save(news);
                    System.out.println("새로운 뉴스 저장 완료 - 제목: " + title + ", ID: " + savedNews.getId());

                } catch (Exception e) {
                    System.out.println("뉴스 항목 처리 중 오류: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("크롤링 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("WebDriver 종료");
            }
        }
    }

    // 두산 베어스 뉴스
    public void DoosanBearsNews() {
        WebDriver driver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");  // 브라우저 창 없이 실행
            driver = new ChromeDriver(options);

            // 두산 베어스 뉴스 페이지 접속
            driver.get("https://www.doosanbears.com/doorundoorun/news");
            System.out.println("두산 베어스 뉴스 페이지 로딩 시작");

            // JavaScript 실행 완료 대기
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.list-box")));

            // 뉴스 목록 가져오기
            List<WebElement> newsElements = driver.findElements(By.cssSelector("ul.list-box > li"));
            System.out.println("찾은 뉴스 개수: "+ newsElements.size());

            for (WebElement element : newsElements) {
                News news = new News();
                news.setTeamName("두산");

                try {
                    // 제목과 URL 추출
                    WebElement titleElement = element.findElement(By.cssSelector("a"));
                    if (titleElement != null) {
                        String title = titleElement.getText();

                        // 중복 체크
                        if (teamNewsRepository.existsByTeamNameAndTitle("두산", title)) {
                            System.out.println("중복된 뉴스 제목: " + title);
                            continue;  // 중복된 경우 다음 뉴스로 건너뛰기
                        }

                        // 중복이 아닌 경우에만 저장
                        news.setTitle(title);
                        news.setUrl(titleElement.getAttribute("href"));
                        System.out.println("제목: " + title);
                    }

                    // 작성 날짜
                    WebElement dateElement = element.findElement(By.cssSelector("p.txt"));
                    if (dateElement != null) {
                        String dateStr = dateElement.getText();

                        // LocalDateTime을 Instant로 변환
                        Instant newsDate = LocalDateTime.parse(dateStr + " 00:00:00",
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                .atZone(ZoneId.systemDefault())
                                .toInstant();

                        news.setNewsDate(newsDate);
                        System.out.println("날짜: " + dateStr);
                    }
                    // DB에 저장
                    if (news.getTitle() != null && !news.getTitle().isEmpty()) {
                        News savedNews = teamNewsRepository.save(news);
                        System.out.println("저장된 뉴스 ID: " + savedNews.getId());
                    }
                } catch (Exception e) {
                    System.out.println("뉴스 항목 처리 중 오류: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("크롤링 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("WebDriver 종료");
            }
        }
    }

    // 삼성 라이온즈 뉴스
    public void SamsungLionsNews() {
        WebDriver driver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);

            driver.get("https://www.samsunglions.com/fan/fan15.asp");
            System.out.println("삼성 라이온즈 뉴스 페이지 로딩 시작");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.list")));

            List<WebElement> newsElements = driver.findElements(By.cssSelector("div.list > ul > li.first"));
            System.out.println("찾은 뉴스 개수: "+ newsElements.size());

            for (WebElement element : newsElements) {
                try {
                    // 제목 추출
                    WebElement titleElement = element.findElement(By.cssSelector("div.txt > p"));
                    if (titleElement == null || titleElement.getText().isEmpty()) {
                        continue;
                    }

                    String title = titleElement.getText();

                    // 중복 체크
                    if (teamNewsRepository.existsByTeamNameAndTitle("삼성라이온즈", title)) {
                        System.out.println("중복된 뉴스 제목: "+ title);
                        continue;
                    }

                    News news = new News();
                    news.setTeamName("삼성");
                    news.setTitle(title);


                    // URL 저장
                    WebElement linkElement = element.findElement(By.cssSelector("div.txt p a"));  // a 태그 직접 선택
                    if (linkElement != null) {
                        String newsUrl = linkElement.getAttribute("href");
                        if (newsUrl != null && !newsUrl.isEmpty()) {
                            if (!newsUrl.startsWith("http")) {
                                newsUrl = "https://www.samsunglions.com" + newsUrl;
                            }
                            news.setUrl(newsUrl);
                            System.out.println("뉴스 URL: "+ newsUrl);
                        }
                    }

                    // 작성 날짜 저장
                    WebElement dateElement = element.findElement(By.cssSelector("span.date"));
                    if (dateElement != null) {
                        String dateStr = dateElement.getText().trim();
                        // "작성일 : " 부분 제거하고 날짜 부분만 추출
                        if (dateStr.contains("작성일 : ")) {
                            dateStr = dateStr.replace("작성일 : ", "").split("\\|")[0].trim();
                            try {
                                // 날짜 형식 변환
                                LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                                // LocalDate를 Instant로 변환
                                Instant newsDate = date.atStartOfDay()
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant();

                                news.setNewsDate(newsDate);
                                System.out.println("날짜: " + dateStr);
                            } catch (Exception e) {
                                System.out.println("날짜 파싱 오류: " + dateStr);
                            }
                        }
                    }

                    // DB에 저장
                    News savedNews = teamNewsRepository.save(news);
                    System.out.println("새로운 뉴스 저장 완료 - 제목: " + title + "ID: " + savedNews.getId());

                } catch (Exception e) {
                    System.out.println("뉴스 항목 처리 중 오류: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("크롤링 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("WebDriver 종료");
            }
        }
    }

    // KIA 타이거즈
    public void KiaTigersNews(){

    }

    // LG 트윈스 뉴스 크롤링
    public void LGTwinsNews() {

    }
}