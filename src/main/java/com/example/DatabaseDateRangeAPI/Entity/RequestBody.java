package com.example.DatabaseDateRangeAPI.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestBody {
    private LocalDate fromDate;
    private LocalDate toDate;
}
