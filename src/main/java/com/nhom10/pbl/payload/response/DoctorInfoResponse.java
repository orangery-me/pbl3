package com.nhom10.pbl.payload.response;

import com.nhom10.pbl.models.Doctor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorInfoResponse {
    private Long id;

    private String position;
    private String description;
    private String RoomAddress;
    private String ServicePrices;

    private DepartmentRespone departmentResponse;
    private UserResponse userModelResponse;

    public static DoctorInfoResponse mapToDoctorInfoRespone(Doctor doctor) {
        return new DoctorInfoResponse(
                doctor.getId(), doctor.getPosition(), doctor.getDescription(),
                doctor.getRoomAddress(),
                doctor.getServicePrices(), DepartmentRespone.mapToDepartmentRespone(doctor.get_department()),
                UserResponse.mapToUserResponse(doctor.getUser()));
    }
}
