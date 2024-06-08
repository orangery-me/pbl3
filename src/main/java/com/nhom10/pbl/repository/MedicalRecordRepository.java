package com.nhom10.pbl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nhom10.pbl.models.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    @Query("SELECT s FROM MedicalRecord s WHERE s.schedule.doctor.id = :doctorId ORDER BY s.schedule.date DESC")
    List<MedicalRecord> findAllByDoctorId(@Param("doctorId") Long doctorId);
}
