package com.Sportagram.sportagram.controller;


import com.Sportagram.sportagram.entity.Rate;
import com.Sportagram.sportagram.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/rates")
public class RatesController {
    @Autowired
    private RatesService ratesService;

    @GetMapping("/user/{userID}")
    public ResponseEntity<Rate> getUserRates(@PathVariable String userID) {
        Rate rate = ratesService.getRateByUserID(userID);
        return ResponseEntity.ok(rate);
    }
}


