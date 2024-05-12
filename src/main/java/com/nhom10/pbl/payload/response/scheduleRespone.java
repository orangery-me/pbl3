package com.nhom10.pbl.payload.response;

import java.sql.Date;

import lombok.Data;

@Data
public class scheduleRespone {
    private Long id;

    private Date date;
    private Integer state;

    private Long _doctorId;

    private Long _patientId;

    private Long _shiftId;
}
