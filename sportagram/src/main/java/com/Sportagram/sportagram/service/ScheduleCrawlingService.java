package com.Sportagram.sportagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sportagram.sportagram.crawler.ScheduleCrawler;

@Service
public class ScheduleCrawlingService {
    private final ScheduleCrawler scheduleCrawler;

    @Autowired
    public ScheduleCrawlingService(ScheduleCrawler scheduleCrawler) {
        this.scheduleCrawler = scheduleCrawler;
    }

    public void performCrawling() {
        scheduleCrawler.scheduleCrawl(); // 크롤링 실행
    }
}