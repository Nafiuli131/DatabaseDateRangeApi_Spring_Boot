package com.example.DatabaseDateRangeAPI.Controller;

import com.example.DatabaseDateRangeAPI.Entity.DataEntryBody;
import com.example.DatabaseDateRangeAPI.Entity.DateRangeEntity;
import com.example.DatabaseDateRangeAPI.Entity.MonthWiseDateValue;
import com.example.DatabaseDateRangeAPI.Entity.YearlyView;
import com.example.DatabaseDateRangeAPI.Service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    RestService restService;

    @PostMapping("/dataEntry")
    public ResponseEntity<DateRangeEntity> addEntity(@RequestBody DataEntryBody dataEntryBody) {
        return ResponseEntity.ok(restService.addData(dataEntryBody));
    }

    @PostMapping("/dayWiseData")
    public ResponseEntity<List<DateRangeEntity>> getDayWiseData(@RequestBody com.example.DatabaseDateRangeAPI.Entity
            .RequestBody requestBody) {
        return ResponseEntity.ok(restService.getDayWiseDate(requestBody));
    }
    @PostMapping("/monthWiseData")
    public ResponseEntity<List<MonthWiseDateValue>> getMonthWiseData(@RequestBody com.example.DatabaseDateRangeAPI.Entity.RequestBody requestBody){
        return ResponseEntity.ok(restService.getMonthWiseDate(requestBody));
    }
    @PostMapping("/yearlyView")
    public ResponseEntity<List<YearlyView>> getYearlyData(@RequestBody com.example.DatabaseDateRangeAPI.Entity.RequestBody requestBody){
        return ResponseEntity.ok(restService.getYearlyData(requestBody));
    }

}
