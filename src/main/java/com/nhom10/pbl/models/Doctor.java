package com.nhom10.pbl.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;
    private String description;
    private String RoomAddress;
    private String ServicePrices;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private department _department;

    @OneToMany(mappedBy = "_doctor", cascade = CascadeType.ALL)
    private List<schedule> listSchedule;
}
