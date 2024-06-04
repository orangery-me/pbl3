package com.nhom10.pbl.dto.respone;

import java.sql.Date;

import lombok.Data;

@Data
public class medicalRecordResponeModel {

    private Long medicalRecordId;

    private String patientName;

    private Date date;

    private shiftRespone shift;

    private String diagnosis;

    private Long scheduleId;
}
