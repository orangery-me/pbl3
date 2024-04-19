package com.nhom10.pbl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.Patient;

@Repository
public interface patientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser_Id(Long id);

}