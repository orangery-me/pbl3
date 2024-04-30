package com.nhom10.pbl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.Doctor;

@Repository
public interface doctorRepository extends JpaRepository<Doctor, Long>{
    Optional<Doctor> findById(Long id);
}
