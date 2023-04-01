package com.example.DatabaseDateRangeAPI.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataEntryBody {
    private LocalDate date;
    private Integer value;
}
