package com.Sportagram.sportagram.controller;


import com.Sportagram.sportagram.dto.DiaryRequest;
import com.Sportagram.sportagram.entity.Diary;
import com.Sportagram.sportagram.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/diary")
public class DiaryController {
    @Autowired
    private DiaryService diaryService;

    @PostMapping("/create")
    public Diary createDiary(@RequestBody DiaryRequest diaryRequest) {
        return diaryService.createDiary(diaryRequest);
    }

    @GetMapping("/{userID}")
    public List<Diary> getDiariesForUser(@PathVariable String userID) {
        return diaryService.getDiariesForUser(userID);
    }

    @GetMapping("/{userID}/{scheduleID}")
    public List<Diary> getDiariesForUserAndScheID(@PathVariable String userID, @PathVariable String scheduleID) {
        return diaryService.getDiariesForUserAndScheID(userID, scheduleID);
    }
}

