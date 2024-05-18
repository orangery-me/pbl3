package com.nhom10.pbl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.shift;

@Repository
public interface ShiftRepository extends JpaRepository<shift, Long> {
    public @NonNull Optional<shift> findById(@NonNull Long id);
}
