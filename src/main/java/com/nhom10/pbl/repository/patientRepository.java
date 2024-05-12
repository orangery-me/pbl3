package com.nhom10.pbl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.Patient;

@Repository
public interface patientRepository extends JpaRepository<Patient, Long> {
    @NonNull
    Optional<Patient> findById(@NonNull Long id);
}
