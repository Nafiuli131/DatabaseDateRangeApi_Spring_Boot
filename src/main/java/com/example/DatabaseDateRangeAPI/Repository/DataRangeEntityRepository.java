package com.example.DatabaseDateRangeAPI.Repository;

import com.example.DatabaseDateRangeAPI.Entity.DateRangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DataRangeEntityRepository extends JpaRepository<DateRangeEntity,Long> {
    @Query("select case when count(dr) > 0 then true else false end from DateRangeEntity dr where dr.date = :date")
    boolean findByDate(LocalDate date);

    List<DateRangeEntity> findByDateBetween(LocalDate fromDate, LocalDate toDate);
}
