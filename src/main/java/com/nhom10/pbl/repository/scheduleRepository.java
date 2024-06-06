package com.nhom10.pbl.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE s.date = :date")
    List<Schedule> findAllByDate(@Param("date") Date date);

    @Query("SELECT s FROM Schedule s WHERE MONTH(s.date) = :month AND YEAR(s.date) = :year")
    List<Schedule> findAllByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @NonNull
    public List<Schedule> findAll();
}
