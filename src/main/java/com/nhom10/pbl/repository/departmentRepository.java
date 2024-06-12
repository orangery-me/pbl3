package com.nhom10.pbl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.Department;

import java.util.*;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    public @NonNull Optional<Department> findById(@NonNull Long id);

    @NonNull
    public List<Department> findAll();
}
