package com.nhom10.pbl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.Shift;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    @NonNull
    public List<Shift> findAll();

    public @NonNull Optional<Shift> findById(@NonNull Long id);
}
