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

import com.nhom10.pbl.models.Doctor;
import com.nhom10.pbl.models.Schedule;
import com.nhom10.pbl.models.Shift;
import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.payload.response.BookingModel;
import com.nhom10.pbl.payload.response.DoctorInfoResponse;
import com.nhom10.pbl.payload.response.DoctorRespone;
import com.nhom10.pbl.payload.response.ScheduleRespone;
import com.nhom10.pbl.payload.response.ShiftRespone;
import com.nhom10.pbl.repository.DoctorRepository;

@Service
public class DoctorServices {

    @Autowired
    private ShiftServices shiftServices;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<DoctorInfoResponse> getAllDoctors() {
        return doctorRepository.findAll().stream().map(DoctorInfoResponse::mapToDoctorInfoRespone).toList();
    }

    public void createNewDoctor(UserModel userModel) {
        Doctor doctor = Doctor.builder().user(userModel).build();
        doctorRepository.save(doctor);
    }

    public List<ScheduleRespone> getListScheduleResponsesOfDoctor(Long doctorId) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        List<ScheduleRespone> listSchedulesRespones = new ArrayList<>();

        if (optionalDoctor.isPresent()) {
            List<Schedule> listSchedules = optionalDoctor.get().getListSchedule();

            for (Schedule schedule : listSchedules) {
                ScheduleRespone scheduleRespone = new ScheduleRespone();
                scheduleRespone.setId(schedule.getId());
                scheduleRespone.setDate(schedule.getDate());
                scheduleRespone.setState(schedule.getState());
                scheduleRespone.setDoctorId(schedule.getDoctor().getId());
                scheduleRespone.setPatientId(schedule.getPatient().getId());
                scheduleRespone.setShiftId(schedule.getShift().getId());

                listSchedulesRespones.add(scheduleRespone);
            }
        }

        return listSchedulesRespones;
    }

    public DoctorRespone getDoctorResponeById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        Doctor _doctorr = doctor.orElseThrow(() -> new RuntimeException("not found"));
        DoctorRespone doctorRespone = new DoctorRespone();
        if (doctor.isPresent()) {
            Doctor _doctor = _doctorr;
            doctorRespone.setId(_doctor.getId());
            doctorRespone.setNameDoctor(_doctor.getUser().getFullName());
            doctorRespone.setGender(_doctor.getUser().getGender());
            doctorRespone.setDescription(_doctor.getDescription());
            doctorRespone.setListSchedule(getListScheduleResponsesOfDoctor(id));
            doctorRespone.setPosition(_doctor.getPosition());
            doctorRespone.setRoomAddress(_doctor.getRoomAddress());
            doctorRespone.setServicePrices(_doctor.getServicePrices());
            doctorRespone.setInitValuedepartmentRespone(_doctor.getDepartment());
        }
        return doctorRespone;
    }

    public List<BookingModel> getListBookingModelsOfDoctor(Long id) {
        DoctorRespone doctorRespone = getDoctorResponeById(id);
        List<BookingModel> ListBookingModel = new ArrayList<>();
        List<Shift> lShifts = shiftServices.getShiftList();
        List<ShiftRespone> lShiftsrRespones = new ArrayList<>();

        for (Shift shift : lShifts) {
            ShiftRespone i = new ShiftRespone();
            i.setId(shift.getId());
            i.setTimeStart(shift.getTime_start());
            i.setTimeEnd(shift.getTime_end());
            lShiftsrRespones.add(i);
        }

        for (int i = 0; i < 30; i++) {

            BookingModel bookingModel = new BookingModel();
            bookingModel.setDay(Date.valueOf(LocalDate.now().plusDays(i)));
            DayOfWeek dayOfWeek = LocalDate.now().plusDays(i).getDayOfWeek();

            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                bookingModel.setListShift(new ArrayList<ShiftRespone>());

            } else {

                List<ShiftRespone> ShiftScheduleResponeOfDay = new ArrayList<>();
                for (ScheduleRespone scheduleRespone : doctorRespone.getListSchedule()) {
                    if (scheduleRespone.getDate().equals(Date.valueOf(LocalDate.now().plusDays(i)))) {

                        ShiftScheduleResponeOfDay
                                .add(lShiftsrRespones.get(scheduleRespone.getShiftId().intValue() - 1));
                    }
                }

                List<ShiftRespone> ListShiftAvailable = new ArrayList<>();
                if (bookingModel.getDay().equals(Date.valueOf(LocalDate.now()))) {

                    for (ShiftRespone shiftRespone : lShiftsrRespones) {
                        if (!ShiftScheduleResponeOfDay.contains(shiftRespone)
                                && LocalTime.now().isBefore(shiftRespone.getTimeStart().minusMinutes(20))) {

                            ListShiftAvailable.add(shiftRespone);
                        }
                    }
                } else {

                    for (ShiftRespone shiftRespone : lShiftsrRespones) {
                        if (!ShiftScheduleResponeOfDay.contains(shiftRespone)) {
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