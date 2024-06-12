package com.nhom10.pbl.payload.response;

import java.sql.Date;

import com.nhom10.pbl.models.Schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRespone {
    private Long id;
    private Date date;
    private Integer state;
    private Long doctorId;
    private Long patientId;
    private Long shiftId;

    public static ScheduleRespone mapToScheduleInfoResponse(Schedule schedule) {
        return new ScheduleRespone(schedule.getId(), schedule.getDate(), schedule.getState(),
                schedule.getDoctor().getId(), schedule.getPatient().getId(), schedule.getShift().getId());
    }
}
