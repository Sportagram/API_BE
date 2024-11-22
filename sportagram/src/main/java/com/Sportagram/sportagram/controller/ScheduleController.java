package com.Sportagram.sportagram.controller;


import com.Sportagram.sportagram.entity.Schedules;
import com.Sportagram.sportagram.service.SchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScheduleController {
    @Autowired
    private SchedulesService scheduleService;

    @GetMapping("api/schedules/{userID}")
    public List<Schedules> getSchedulesForUser(@PathVariable String userID) {
        return scheduleService.getSchedulesForUser(userID);
    }
}


