package com.example.DatabaseDateRangeAPI.Service;

import com.example.DatabaseDateRangeAPI.Entity.DataEntryBody;
import com.example.DatabaseDateRangeAPI.Entity.DateRangeEntity;
import com.example.DatabaseDateRangeAPI.Entity.MonthWiseDateValue;
import com.example.DatabaseDateRangeAPI.Entity.YearlyView;
import com.example.DatabaseDateRangeAPI.Repository.DataRangeEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestService {
    @Autowired
    DataRangeEntityRepository dataRangeEntityRepository;
    public DateRangeEntity addData(DataEntryBody dataEntryBody) {
        DateRangeEntity dateRangeEntity = new DateRangeEntity();
        if(!dataRangeEntityRepository.findByDate(dataEntryBody.getDate())){
            dateRangeEntity.setDate(dataEntryBody.getDate());
            dateRangeEntity.setValue(dataEntryBody.getValue());
            dataRangeEntityRepository.save(dateRangeEntity);
        }
        return dateRangeEntity;
    }

    public List<DateRangeEntity> getDayWiseDate(@RequestBody com.example.DatabaseDateRangeAPI.Entity.RequestBody requestBody) {
        List<DateRangeEntity> dateRangeEntities = new ArrayList<>();
        List<DateRangeEntity> dateRangeEntityList = dataRangeEntityRepository.findByDateBetween(requestBody.getFromDate(),
                requestBody.getToDate());
        Map<LocalDate,DateRangeEntity> entityMap = new HashMap<>();
        for(DateRangeEntity dateRangeEntity : dateRangeEntityList){
            entityMap.put(dateRangeEntity.getDate(),dateRangeEntity);
        }
        for (LocalDate date = requestBody.getFromDate(); !date.isAfter(requestBody.getToDate()); date = date.plusDays(1)) {
            if(entityMap.containsKey(date)){
                dateRangeEntities.add(entityMap.get(date));
            }else{
                DateRangeEntity dateRangeEntity = new DateRangeEntity();
                dateRangeEntity.setDate(date);
                dateRangeEntity.setValue(420);
                dateRangeEntities.add(dateRangeEntity);
            }
        }
        return  dataRangeEntityRepository.saveAll(dateRangeEntities);
    }

    public List<MonthWiseDateValue> getMonthWiseDate(com.example.DatabaseDateRangeAPI.Entity.RequestBody requestBody) {
        List<MonthWiseDateValue> monthWiseDateValueList = new ArrayList<>();
        for (LocalDate date = requestBody.getFromDate(); date.isBefore(requestBody.getToDate().plusDays(1));
             date = date.plusMonths(1)) {
            com.example.DatabaseDateRangeAPI.Entity.RequestBody sentRequestBody = new com.example.DatabaseDateRangeAPI.Entity.RequestBody();
            sentRequestBody.setFromDate(date);
            sentRequestBody.setToDate(date.withDayOfMonth(date.lengthOfMonth()));
            List<DateRangeEntity> dayWiseDate = getDayWiseDate(sentRequestBody);
            Long valuesSum = dayWiseDate.stream()
                    .mapToLong(DateRangeEntity::getValue)
                    .sum();
            MonthWiseDateValue monthWiseDateValue = new MonthWiseDateValue();
            monthWiseDateValue.setYear(date.getYear());
            monthWiseDateValue.setMonth(date.getMonth().getValue());
            monthWiseDateValue.setTotalValue(valuesSum);
            monthWiseDateValueList.add(monthWiseDateValue);
        }

        return monthWiseDateValueList;
    }

    public List<YearlyView> getYearlyData(com.example.DatabaseDateRangeAPI.Entity.RequestBody requestBody) {
        List<MonthWiseDateValue> monthWiseDateValueList = getMonthWiseDate(requestBody);
        Map<Integer, List<MonthWiseDateValue>> groupedByYear = monthWiseDateValueList.stream()
                .collect(Collectors.groupingBy(MonthWiseDateValue::getYear));
        return groupedByYear.entrySet().stream()
                .map(entry -> {
                    Integer year = entry.getKey();
                    Long value = entry.getValue().stream()
                            .mapToLong(MonthWiseDateValue::getTotalValue)
                            .sum();
                    return new YearlyView(year, value);
                })
                .toList();
    }
}
