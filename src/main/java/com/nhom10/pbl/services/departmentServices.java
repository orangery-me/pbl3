package com.nhom10.pbl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.Doctor;
import com.nhom10.pbl.models.department;
import com.nhom10.pbl.models.schedule;
import com.nhom10.pbl.models.shift;
import com.nhom10.pbl.payload.response.departmentRespone;
import com.nhom10.pbl.payload.response.doctorRespone;
import com.nhom10.pbl.payload.response.scheduleRespone;
import com.nhom10.pbl.repository.departmentRepository;
import com.nhom10.pbl.repository.shiftRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.*;

@Service
public class departmentServices {
    @Autowired
    private shiftRepository shiftRepository;

    @Autowired
    private shiftServices shiftServices;

    @Autowired
    private departmentRepository departmentRepo;

    public List<departmentRespone> getAllDepartmentRespones() {
        List<departmentRespone> departmentRespones = new ArrayList<>();

        List<department> listDepartments = departmentRepo.findAll();
        for (department i : listDepartments) {
            departmentRespone _departmentRespone = new departmentRespone();
            _departmentRespone.setId(i.getId());
            _departmentRespone.setNameDepartment(i.getNameDepartment());
            _departmentRespone.setDescriptionDepartment(i.getDescriptionDepartment());
            _departmentRespone.setLocation(i.getLocation());

            departmentRespones.add(_departmentRespone);
        }
        return departmentRespones;
    }

    public departmentRespone getDepartmentByID(Long id) {
        Optional<department> departmentOptional = departmentRepo.findById(id);
        if (departmentOptional.isPresent()) {
            department _department = departmentOptional.get();
            departmentRespone _departmentRespone = new departmentRespone();
            _departmentRespone.setId(_department.getId());
            _departmentRespone.setDescriptionDepartment(_department.getDescriptionDepartment());
            _departmentRespone.setLocation(_department.getLocation());
            _departmentRespone.setNameDepartment(_department.getNameDepartment());
            return _departmentRespone;
        }
        return null;
    }

    public List<doctorRespone> getListDoctor(Long id, boolean addSchedule) {
        List<doctorRespone> listDoctorRespone = new ArrayList<>();
        Optional<department> departmentOptional = departmentRepo.findById(id);

        if (departmentOptional.isPresent()) {
            List<Doctor> listDoctor = departmentOptional.get().getListDoctors();
            for (Doctor doctor : listDoctor) {

                doctorRespone _DoctorRespone = new doctorRespone();
                _DoctorRespone.setId(doctor.getId());
                _DoctorRespone.setDescription(doctor.getDescription());
                _DoctorRespone.setPosition(doctor.getPosition());
                _DoctorRespone.setRoomAddress(doctor.getRoomAddress());
                _DoctorRespone.setServicePrices(doctor.getServicePrices());
                _DoctorRespone.setInitValuedepartmentRespone(doctor.get_department());

                if (addSchedule) {
                    List<scheduleRespone> scheduleRespones = new ArrayList<>();
                    for (schedule schedule : doctor.getListSchedule()) {
                        scheduleRespone scheduleRespone = new scheduleRespone();
                        scheduleRespone.setId(schedule.getId());
                        scheduleRespone.setDate(schedule.getDate());
                        scheduleRespone.setState(schedule.getState());
                        scheduleRespone.set_doctorId(schedule.get_doctor().getId());
                        scheduleRespone.set_patientId(schedule.get_patient().getId());
                        scheduleRespone.set_shiftId(schedule.get_shift().getId());
                        scheduleRespones.add(scheduleRespone);
                    }
                    _DoctorRespone.setListSchedule(scheduleRespones);
                }

                listDoctorRespone.add(_DoctorRespone);
            }
        }
        return listDoctorRespone;
    }

    public List<doctorRespone> listDoctorToday(Long id) {

        List<shift> listShifts = shiftServices.getShiftList();

        List<doctorRespone> listResponses = getListDoctor(id, true);

        List<doctorRespone> listDoctorToday = new ArrayList<>();

        for (doctorRespone doctor : listResponses) {

            List<shift> listShiftsBookedFromNow = new ArrayList<>();

            for (scheduleRespone schedule : doctor.getListSchedule()) {

                if (schedule.getDate().equals(Date.valueOf(LocalDate.now()))
                        && shiftRepository.findById(schedule.get_shiftId()).get().getTime_start()
                                .isAfter(LocalTime.now())) {
                    listShiftsBookedFromNow.add(shiftRepository.findById(schedule.get_shiftId()).get());
                }
            }

            List<shift> listShiftsFromNow = new ArrayList<>();

            for (shift shift : listShifts) {
                if (!listShiftsBookedFromNow.contains(shift) && shift.getTime_start().isAfter(LocalTime.now())) {
                    listShiftsFromNow.add(shift);
                }
            }

            if (listShiftsFromNow.size() > listShiftsBookedFromNow.size()) {
                listDoctorToday.add(doctor);
            }
        }
        return listDoctorToday;
    }

    public List<doctorRespone> listDoctorTomorrow(Long id) {

        List<shift> listShifts = shiftServices.getShiftList();

        List<doctorRespone> listResponses = getListDoctor(id, true);

        List<doctorRespone> listDoctorTomorrow = new ArrayList<>();

        for (doctorRespone doctor : listResponses) {

            List<shift> listShiftsBookedTomorrow = new ArrayList<>();

            for (scheduleRespone schedule : doctor.getListSchedule()) {

                if (schedule.getDate().equals(Date.valueOf(LocalDate.now().plusDays(1)))) {
                    listShiftsBookedTomorrow.add(shiftRepository.findById(schedule.get_shiftId()).get());
                }
            }

            if (listShifts.size() > listShiftsBookedTomorrow.size()) {
                listDoctorTomorrow.add(doctor);
            }
        }
        return listDoctorTomorrow;
    }

    public List<doctorRespone> listDoctorNextSevenDay(Long id) {

        List<shift> listShifts = shiftServices.getShiftList();

        List<doctorRespone> listResponses = getListDoctor(id, true);

        List<doctorRespone> listDoctorNextSevenDay = new ArrayList<>();

        for (doctorRespone doctor : listResponses) {

            List<shift> listShiftsBookedNextSevenDay = new ArrayList<>();

            for (scheduleRespone schedule : doctor.getListSchedule()) {

                if (schedule.getDate().after(Date.valueOf(LocalDate.now().plusDays(1)))
                        && schedule.getDate().before(Date.valueOf(LocalDate.now().plusDays(7)))) {
                    listShiftsBookedNextSevenDay.add(shiftRepository.findById(schedule.get_shiftId()).get());
                }
            }

            if (listShifts.size() > listShiftsBookedNextSevenDay.size()) {
                listDoctorNextSevenDay.add(doctor);
            }
        }
        return listDoctorNextSevenDay;
    }
}