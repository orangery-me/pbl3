package com.nhom10.pbl.payload.resquest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorRequest {
    private String position;
    private String description;
    private String RoomAddress;
    private Integer ServicePrices;
    private Long department_id;
    private Long user_id;
}