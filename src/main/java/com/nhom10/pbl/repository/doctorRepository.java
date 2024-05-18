package com.nhom10.pbl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.nhom10.pbl.models.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    public @NonNull Optional<Doctor> findById(@NonNull Long id);

    @NonNull
    public List<Doctor> findAll();
}
