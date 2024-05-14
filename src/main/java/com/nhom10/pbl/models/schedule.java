package com.nhom10.pbl.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "schedule")
public class schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private Integer state;

    @ManyToOne
    @JoinColumn(name = "Doctor_id")
    private Doctor _doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient _patient;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private shift _shift;

}
