package com.nhom10.pbl.payload.response;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class BookingModel {

    private Date day;
    private List<ShiftResponse> ListShift;

}
