package com.nhom10.pbl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhom10.pbl.models.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long>{

}
