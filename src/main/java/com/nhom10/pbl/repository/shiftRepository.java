package com.nhom10.pbl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.shift;

@Repository
public interface shiftRepository extends JpaRepository<shift, Long> {
    Optional<shift> findById(Long id);
}
