package com.nhom10.pbl.dto.respone;

import java.time.LocalTime;

import lombok.Data;

@Data
public class shiftRespone {

    private Long Id;

    private LocalTime timeStart;
    private LocalTime timeEnd;
}
