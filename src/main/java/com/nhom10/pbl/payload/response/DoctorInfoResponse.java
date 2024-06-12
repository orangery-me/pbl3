package com.nhom10.pbl.payload.response;

import java.sql.Date;

import com.nhom10.pbl.models.Doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorInfoResponse {
    private Long id;

    private String position;
    private String description;
    private String RoomAddress;
    private Integer ServicePrices;
    private String departmentName;
    private String fullname;
    private Date birthday;

    public static DoctorInfoResponse mapToDoctorInfoRespone(Doctor doctor) {
        String departmentName = null;
        if (doctor.getDepartment() != null)
            departmentName = doctor.getDepartment().getNameDepartment();
        return new DoctorInfoResponse(doctor.getId(), doctor.getPosition(),
                doctor.getDescription(),
                doctor.getRoomAddress(), doctor.getServicePrices(),
                departmentName,
                doctor.getUser().getFullname(), doctor.getUser().getBirthday());
    }
}
