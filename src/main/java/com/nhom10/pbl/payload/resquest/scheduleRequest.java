package com.nhom10.pbl.payload.resquest;

import lombok.Data;

import java.sql.Date;

@Data
public class ScheduleRequest {
    private Date date;

    private Integer state;

    private Long doctorID;

    private Long patientID;

    private Long shiftID;
}
