package com.nhom10.pbl.services;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhom10.pbl.dto.respone.bookingModel;
import com.nhom10.pbl.dto.respone.doctorRespone;
import com.nhom10.pbl.dto.respone.scheduleRespone;
import com.nhom10.pbl.dto.respone.shiftRespone;
import com.nhom10.pbl.models.Doctor;
import com.nhom10.pbl.models.schedule;
import com.nhom10.pbl.models.shift;
import com.nhom10.pbl.repository.doctorRepository;

@Service
public class doctorServices {

    @Autowired
    private shiftServices shiftServices;

    @Autowired
    private doctorRepository doctorRepository;

    public List<scheduleRespone> getListScheduleResponsesOfDoctor(Long doctorId){
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        List<scheduleRespone> listSchedulesRespones = new ArrayList<>();

        if(optionalDoctor.isPresent()){
            List<schedule> listSchedules = optionalDoctor.get().getListSchedule();

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
        }

        return listSchedulesRespones;
    }

    public doctorRespone getDoctorResponeById(Long id){
        Optional<Doctor> doctor = doctorRepository.findById(id);
        Doctor _doctorr = doctor.orElseThrow(()-> new RuntimeException("not found"));
        doctorRespone doctorRespone = new doctorRespone();
        if(doctor.isPresent()){
            Doctor _doctor = _doctorr;
            doctorRespone.setId(_doctor.getId());
            doctorRespone.setDescription(_doctor.getDescription());
            doctorRespone.setListSchedule(getListScheduleResponsesOfDoctor(id));
            doctorRespone.setPosition(_doctor.getPosition());
            doctorRespone.setRoomAddress(_doctor.getRoomAddress());
            doctorRespone.setServicePrices(_doctor.getServicePrices());
            doctorRespone.setInitValuedepartmentRespone(_doctor.get_department());
        }
        return doctorRespone;
    }

    public List<bookingModel> getListBookingModelsOfDoctor(Long id) {
        doctorRespone doctorRespone = getDoctorResponeById(id);
        List<bookingModel> ListBookingModel = new ArrayList<>();
        List<shift> lShifts = shiftServices.getShiftList();
        List<shiftRespone> lShiftsrRespones = new ArrayList<>();
        
        for (shift shift : lShifts) {
            shiftRespone i = new shiftRespone();
            i.setId(shift.getId());
            i.setTimeStart(shift.getTime_start());
            i.setTimeEnd(shift.getTime_end());
            lShiftsrRespones.add(i);
        }

        for(int i = 0; i < 30; i++){

            bookingModel bookingModel = new bookingModel();
            bookingModel.setDay(Date.valueOf(LocalDate.now().plusDays(i)));
            DayOfWeek dayOfWeek = LocalDate.now().plusDays(i).getDayOfWeek();

            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                bookingModel.setListShift(new ArrayList<shiftRespone>());

            }else{

                List<shiftRespone> ShiftScheduleResponeOfDay = new ArrayList<>();
                for (scheduleRespone scheduleRespone : doctorRespone.getListSchedule()) {
                    if(scheduleRespone.getDate().equals(Date.valueOf(LocalDate.now().plusDays(i)))){

                        ShiftScheduleResponeOfDay.add(lShiftsrRespones.get(scheduleRespone.get_shiftId().intValue()-1));
                    }
                }
                
                List<shiftRespone> ListShiftAvailable = new ArrayList<>();
                if(bookingModel.getDay().equals(Date.valueOf(LocalDate.now()))){

                    for (shiftRespone shiftRespone : lShiftsrRespones) {
                        if(!ShiftScheduleResponeOfDay.contains(shiftRespone)
                        && LocalTime.now().isBefore(shiftRespone.getTimeStart().minusMinutes(20)))
                        {

                            ListShiftAvailable.add(shiftRespone);
                        }
                    }
                }else{

                    for (shiftRespone shiftRespone : lShiftsrRespones) {
                        if(!ShiftScheduleResponeOfDay.contains(shiftRespone)){
                            ListShiftAvailable.add(shiftRespone);
                        }
                    }
                }
                
                bookingModel.setListShift(ListShiftAvailable);
            }
            ListBookingModel.add(bookingModel);
        }

        return ListBookingModel;
    }

}
