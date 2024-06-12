package com.nhom10.pbl.payload.response;

import java.sql.Date;
import java.time.LocalTime;

import com.nhom10.pbl.models.Schedule;
import com.nhom10.pbl.models.ScheduleState;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ScheduleInfoResponse {
    private Long id;
    private Date date;
    private String state;
    private Long doctorId;
    private String doctorName;
    private String location;
    private Long patientId;
    private String patientName;
    private Long shiftId;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private Integer servicePrice;

    public static ScheduleInfoResponse mapToScheduleInfoResponse(Schedule schedule) {
        return new ScheduleInfoResponse(schedule.getId(), schedule.getDate(),
                ScheduleState.getState(schedule.getState()),
                schedule.getDoctor().getId(), schedule.getDoctor().getUser().getFullname(),
                schedule.getDoctor().getRoomAddress(),
                schedule.getPatient().getId(), schedule.getPatient().getUser().getFullname(),
                schedule.getShift().getId(),
                schedule.getShift().getTime_start(), schedule.getShift().getTime_end(),
                schedule.getDoctor().getServicePrices());
    }
}
