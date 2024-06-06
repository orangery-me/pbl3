package com.nhom10.pbl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    public @NonNull Optional<Patient> findById(@NonNull Long id);

    @NonNull
    public List<Patient> findAll();
}
