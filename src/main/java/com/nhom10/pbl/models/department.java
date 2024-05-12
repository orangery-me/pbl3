package com.nhom10.pbl.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "department")
public class department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String NameDepartment;
    private String DescriptionDepartment;
    private String Location;

    @OneToMany(mappedBy = "_department", cascade = CascadeType.ALL)
    private List<Doctor> listDoctors;
}