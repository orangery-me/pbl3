package com.nhom10.pbl.dto.request;

import lombok.Data;

import java.sql.Date;

@Data
public class scheduleRequest {
    private Date date;

    private Integer state;

    private Long doctorID;

    private Long patientID; 

    private Long shiftID;
}
