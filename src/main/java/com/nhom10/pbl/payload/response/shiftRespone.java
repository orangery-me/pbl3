package com.nhom10.pbl.payload.response;

import java.time.LocalTime;

import lombok.Data;

@Data
public class shiftRespone {

    private Long Id;

    private LocalTime timeStart;
    private LocalTime timeEnd;
}
