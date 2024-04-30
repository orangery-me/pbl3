package com.nhom10.pbl.dto.respone;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class bookingModel {

    private Date day;
    private List<shiftRespone> ListShift;
    
}
