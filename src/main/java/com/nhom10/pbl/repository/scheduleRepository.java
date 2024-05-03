package com.nhom10.pbl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.schedule;

@Repository
public interface scheduleRepository extends JpaRepository<schedule, Long>{

}
