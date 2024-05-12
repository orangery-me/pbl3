package com.nhom10.pbl.payload.response;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class bookingModel {

    private Date day;
    private List<shiftRespone> ListShift;

}
