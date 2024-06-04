package com.nhom10.pbl.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.schedule;

@Repository
public interface scheduleRepository extends JpaRepository<schedule, Long>{

    @Query("SELECT s FROM schedule s WHERE s.date = :date")
    List<schedule> findAllByDate(@Param("date") Date date);
}
