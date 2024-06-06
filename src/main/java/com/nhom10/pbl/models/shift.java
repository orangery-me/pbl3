package com.nhom10.pbl.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalTime;

import lombok.Data;

@Entity
@Data
@Table(name = "shift")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime time_start;
    private LocalTime time_end;

    @OneToMany(mappedBy = "shift")
    private List<Schedule> listSchedules;
}
