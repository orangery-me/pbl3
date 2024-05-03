package com.nhom10.pbl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.department;
import java.util.*;


@Repository
public interface departmentRepository extends  JpaRepository<department, Long>{
    Optional<department> findById(Long id);
}
