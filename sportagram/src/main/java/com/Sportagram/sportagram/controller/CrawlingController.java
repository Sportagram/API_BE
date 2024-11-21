package com.Sportagram.sportagram.controller;

import com.Sportagram.sportagram.service.ScheduleCrawlingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CrawlingController {
    private final ScheduleCrawlingService scheduleCrawlingService;

    public CrawlingController(ScheduleCrawlingService scheduleCrawlingService) {
        this.scheduleCrawlingService = scheduleCrawlingService;
    }

    @GetMapping("api/scheduleCrawl")
    public String scheduleCrawl() {
        scheduleCrawlingService.performCrawling();
        return "Crawling performed. Check console for output.";
    }
}