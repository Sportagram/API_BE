package com.Sportagram.sportagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Sportagram.sportagram.crawler.ScoreCrawler;

@Service
public class ScoreCrawlingService {
    private final ScoreCrawler scoreCrawler;

    @Autowired
    public ScoreCrawlingService(ScoreCrawler scoreCrawler) {
        this.scoreCrawler = scoreCrawler;
    }

    public void performCrawling(String scheduleID) {
        scoreCrawler.scoreCrawl(scheduleID); // 크롤링 실행
    }
}
