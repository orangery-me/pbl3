package com.nhom10.pbl.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.Schedule;
import com.nhom10.pbl.payload.response.ScheduleInfoResponse;
import com.nhom10.pbl.payload.response.ScheduleRespone;
import com.nhom10.pbl.payload.resquest.ScheduleRequest;
import com.nhom10.pbl.repository.DoctorRepository;
import com.nhom10.pbl.repository.PatientRepository;
import com.nhom10.pbl.repository.ScheduleRepository;
import com.nhom10.pbl.repository.ShiftRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleServices {

    private final ScheduleRepository schedule_Repository;
    private final ShiftRepository shift_Repository;
    private final DoctorRepository doctor_Repository;
    private final PatientRepository patient_Repository;
    private final MedicalRecordServices medicalRecordServices;

    public void createSchedule(ScheduleRequest request) {
        Schedule _Schedule = new Schedule();

        _Schedule.setDate(request.getDate());
        _Schedule.setState(request.getState());
        doctor_Repository.findById(request.getDoctorID())
                .ifPresent(_Schedule::setDoctor);

        patient_Repository.findById(request.getPatientID())
                .ifPresent(_Schedule::setPatient);

        shift_Repository.findById(request.getShiftID())
                .ifPresent(_Schedule::setShift);

        schedule_Repository.save(_Schedule);

        medicalRecordServices.CreateMedicalRecord(_Schedule);
    }

    public List<ScheduleRespone> getAllschedules() {
        return schedule_Repository.findAll().stream().map(ScheduleRespone::mapToScheduleInfoResponse)
                .collect(Collectors.toList());
    }

    public List<ScheduleInfoResponse> getAllScheduleInfo() {
        return schedule_Repository.findAll().stream().map(ScheduleInfoResponse::mapToScheduleInfoResponse)
                .collect(Collectors.toList());
    }

    public List<Schedule> getListSchedulesByDate(Date date) {
        List<Schedule> lSchedules = new ArrayList<>();
        try {
            lSchedules = schedule_Repository.findAllByDate(date);

        } catch (Exception e) {
        }

        return lSchedules;
    }

    public Long getRevenueOfDay(Date date) {
        List<Schedule> lSchedules = getListSchedulesByDate(date);
        Long total = Long.valueOf(0);

        for (Schedule schedule : lSchedules) {
            total += schedule.getDoctor().getServicePrices();
        }

        return total;
    }

    public List<Schedule> getListSchedulesByMonthAndYear(int month, int year) {
        return schedule_Repository.findAllByMonthAndYear(month, year);
    }

    public Long getRevenueOfMonth(int month, int year) {
        List<Schedule> lSchedules = getListSchedulesByMonthAndYear(month, year);
        Long total = Long.valueOf(0);

        for (Schedule schedule : lSchedules) {
            total += schedule.getDoctor().getServicePrices();
        }

        return total;
    }
}