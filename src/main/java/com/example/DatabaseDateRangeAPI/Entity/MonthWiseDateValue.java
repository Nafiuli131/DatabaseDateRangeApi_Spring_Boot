package com.example.DatabaseDateRangeAPI.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthWiseDateValue {
    private Integer month;
    private Integer year;
    private Long totalValue;
}
