package com.nhom10.pbl.payload.response;

import java.sql.Date;

import lombok.Data;

@Data
public class MedicalRecordResponeModel {

    private Long medicalRecordId;

    private String patientName;

    private Date date;

    private ShiftRespone shift;

    private String diagnosis;

    private Long scheduleId;
}
