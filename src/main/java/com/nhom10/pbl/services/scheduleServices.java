package com.nhom10.pbl.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom10.pbl.dto.request.scheduleRequest;
import com.nhom10.pbl.dto.respone.scheduleRespone;
import com.nhom10.pbl.models.schedule;
import com.nhom10.pbl.repository.doctorRepository;
import com.nhom10.pbl.repository.patientRepository;
import com.nhom10.pbl.repository.scheduleRepository;
import com.nhom10.pbl.repository.shiftRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class scheduleServices {

    private final scheduleRepository schedule_Repository;
    private final shiftRepository shift_Repository;
    private final doctorRepository doctor_Repository;
    private final patientRepository patient_Repository;
    private final MedicalRecordServices medicalRecordServices;

    public void createSchedule(scheduleRequest request){
        schedule _Schedule = new schedule();

        _Schedule.setDate(request.getDate());
        _Schedule.setState(request.getState());
        doctor_Repository.findById(request.getDoctorID())
            .ifPresent(_Schedule::set_doctor);
        
        patient_Repository.findById(request.getPatientID())
            .ifPresent(_Schedule::set_patient);

        shift_Repository.findById(request.getShiftID())
            .ifPresent(_Schedule::set_shift);

        schedule_Repository.save(_Schedule);

        medicalRecordServices.CreateMedicalRecord(_Schedule);
    }


    public List<scheduleRespone> getAllschedule(){
       List<schedule> listSchedules = schedule_Repository.findAll();
       List<scheduleRespone> listSchedulesRespones = new ArrayList<>();

        for (schedule schedule : listSchedules) {
            scheduleRespone scheduleRespone = new scheduleRespone();
            scheduleRespone.setId(schedule.getId());
            scheduleRespone.setDate(schedule.getDate());
            scheduleRespone.setState(schedule.getState());
            scheduleRespone.set_doctorId(schedule.get_doctor().getId());
            scheduleRespone.set_patientId(schedule.get_patient().getId());
            scheduleRespone.set_shiftId(schedule.get_shift().getId());

            listSchedulesRespones.add(scheduleRespone);
        }

        return listSchedulesRespones;
    }

    public List<schedule> getListSchedulesByDate(Date date){
        List<schedule> lSchedules = new ArrayList<>();
        try {
            lSchedules = schedule_Repository.findAllByDate(date);
        
        } catch (Exception e) {}

        return lSchedules;
    }

    public Long getRevenueOfDay(Date date){
        List<schedule> lSchedules = getListSchedulesByDate(date);
        Long total = Long.valueOf(0);

        for (schedule schedule : lSchedules) {
            total += schedule.get_doctor().getServicePrices();
        }

        return total;
    }
}
