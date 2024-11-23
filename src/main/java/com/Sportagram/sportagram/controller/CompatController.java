package com.Sportagram.sportagram.controller;


import com.Sportagram.sportagram.entity.Compatibility;
import com.Sportagram.sportagram.service.CompatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("api/compat")
public class CompatController {
    @Autowired
    private CompatService compatService;

    @Autowired
    public CompatController(CompatService compatService) {
        this.compatService = compatService;
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<List<Compatibility>> getCompatibilityByUserID(@PathVariable String userID) {
        List<Compatibility> compatibilityData = compatService.getCompatibilityDataByUserID(userID);
        if (compatibilityData.isEmpty()) {
            return ResponseEntity.noContent().build(); // 데이터 없으면 204 No Content 반환
        }
        return ResponseEntity.ok(compatibilityData); // 데이터 있으면 200 OK와 함께 반환
    }

    @GetMapping("/user/{userID}/highest-win-rate")
    public ResponseEntity<Compatibility> getHighestWinRateByUserID(@PathVariable String userID) {
        Compatibility highestWinRate = compatService.getHighestWinRateByUserID(userID);
        if (highestWinRate == null) {
            return ResponseEntity.noContent().build(); // 데이터 없으면 204 No Content 반환
        }
        return ResponseEntity.ok(highestWinRate); // 데이터 있으면 200 OK와 함께 반환
    }

    @GetMapping("/user/{userID}/highest-lose-rate")
    public ResponseEntity<Compatibility> getHighestLoseRateByUserID(@PathVariable String userID) {
        Compatibility highestLoseRate = compatService.getHighestLoseRateByUserID(userID);
        if (highestLoseRate == null) {
            return ResponseEntity.noContent().build(); // 데이터 없으면 204 No Content 반환
        }
        return ResponseEntity.ok(highestLoseRate); // 데이터 있으면 200 OK와 함께 반환
    }
}


