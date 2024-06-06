package com.nhom10.pbl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.Department;
import com.nhom10.pbl.models.Doctor;
import com.nhom10.pbl.models.Schedule;
import com.nhom10.pbl.models.Shift;
import com.nhom10.pbl.payload.response.DepartmentRespone;
import com.nhom10.pbl.payload.response.DoctorRespone;
import com.nhom10.pbl.payload.response.ScheduleRespone;
import com.nhom10.pbl.payload.resquest.DepartmentRequest;
import com.nhom10.pbl.repository.DepartmentRepository;
import com.nhom10.pbl.repository.ShiftRepository;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class DepartmentServices {
    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private ShiftServices shiftServices;

    @Autowired
    private DepartmentRepository departmentRepo;

    public DepartmentRespone createDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder().NameDepartment(departmentRequest.getNameDepartment())
                .DescriptionDepartment(departmentRequest.getDescriptionDepartment())
                .Location(departmentRequest.getLocation())
                .build();
        departmentRepo.save(department);
        return DepartmentRespone.mapToDepartmentRespone(department);
    }

    public List<DepartmentRespone> getAllDepartmentRespones() {
        return departmentRepo.findAll().stream().map(DepartmentRespone::mapToDepartmentRespone).toList();
    }

    public DepartmentRespone getDepartmentByID(Long id) {
        Optional<Department> departmentOptional = departmentRepo.findById(id);
        if (departmentOptional.isPresent()) {
            Department _department = departmentOptional.get();
            DepartmentRespone _departmentRespone = new DepartmentRespone();
            _departmentRespone.setId(_department.getId());
            _departmentRespone.setDescriptionDepartment(_department.getDescriptionDepartment());
            _departmentRespone.setLocation(_department.getLocation());
            _departmentRespone.setNameDepartment(_department.getNameDepartment());
            return _departmentRespone;
        }
        return null;
    }

    public List<DoctorRespone> getListDoctor(Long id, boolean addSchedule) {
        List<DoctorRespone> listDoctorRespone = new ArrayList<>();
        Optional<Department> departmentOptional = departmentRepo.findById(id);

        if (departmentOptional.isPresent()) {
            List<Doctor> listDoctor = departmentOptional.get().getListDoctors();
            for (Doctor doctor : listDoctor) {
                DoctorRespone _DoctorRespone = new DoctorRespone();
                _DoctorRespone.setId(doctor.getId());
                _DoctorRespone.setNameDoctor(doctor.getUser().getFullName());
                _DoctorRespone.setGender(doctor.getUser().getGender());
                _DoctorRespone.setDescription(doctor.getDescription());
                _DoctorRespone.setPosition(doctor.getPosition());
                _DoctorRespone.setRoomAddress(doctor.getRoomAddress());
                _DoctorRespone.setServicePrices(doctor.getServicePrices());
                _DoctorRespone.setInitValuedepartmentRespone(doctor.getDepartment());

                if (addSchedule) {
                    List<ScheduleRespone> scheduleRespones = new ArrayList<>();
                    for (Schedule schedule : doctor.getListSchedule()) {
                        ScheduleRespone scheduleRespone = new ScheduleRespone();
                        scheduleRespone.setId(schedule.getId());
                        scheduleRespone.setDate(schedule.getDate());
                        scheduleRespone.setState(schedule.getState());
                        scheduleRespone.setDoctorId(schedule.getDoctor().getId());
                        scheduleRespone.setPatientId(schedule.getPatient().getId());
                        scheduleRespone.setShiftId(schedule.getShift().getId());
                        scheduleRespones.add(scheduleRespone);
                    }
                    _DoctorRespone.setListSchedule(scheduleRespones);
                }

                listDoctorRespone.add(_DoctorRespone);
            }
        }
        return listDoctorRespone;
    }

    public List<DoctorRespone> listDoctorToday(Long id) {

        List<Shift> listShifts = shiftServices.getShiftList();

        List<DoctorRespone> listResponses = getListDoctor(id, true);

        List<DoctorRespone> listDoctorToday = new ArrayList<>();

        if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            return listDoctorToday;
        }

        for (DoctorRespone doctor : listResponses) {

            List<Shift> listShiftsBookedFromNow = new ArrayList<>();

            for (ScheduleRespone schedule : doctor.getListSchedule()) {

                if (schedule.getDate().equals(Date.valueOf(LocalDate.now()))
                        && shiftRepository.findById(schedule.getShiftId()).get().getTime_start()
                                .isAfter(LocalTime.now())) {
                    listShiftsBookedFromNow.add(shiftRepository.findById(schedule.getShiftId()).get());
                }
            }

            List<Shift> listShiftsFromNow = new ArrayList<>();

            for (Shift shift : listShifts) {
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

    public List<DoctorRespone> listDoctorTomorrow(Long id) {

        List<Shift> listShifts = shiftServices.getShiftList();

        List<DoctorRespone> listResponses = getListDoctor(id, true);

        List<DoctorRespone> listDoctorTomorrow = new ArrayList<>();

        for (DoctorRespone doctor : listResponses) {

            List<Shift> listShiftsBookedTomorrow = new ArrayList<>();

            for (ScheduleRespone schedule : doctor.getListSchedule()) {

                if (schedule.getDate().equals(Date.valueOf(LocalDate.now().plusDays(1)))) {
                    listShiftsBookedTomorrow.add(shiftRepository.findById(schedule.getShiftId()).get());
                }
            }

            if (listShifts.size() > listShiftsBookedTomorrow.size()) {
                listDoctorTomorrow.add(doctor);
            }
        }
        return listDoctorTomorrow;
    }

    public List<DoctorRespone> listDoctorNextSevenDay(Long id) {

        List<Shift> listShifts = shiftServices.getShiftList();

        List<DoctorRespone> listResponses = getListDoctor(id, true);

        List<DoctorRespone> listDoctorNextSevenDay = new ArrayList<>();

        for (DoctorRespone doctor : listResponses) {

            List<Shift> listShiftsBookedNextSevenDay = new ArrayList<>();

            for (ScheduleRespone schedule : doctor.getListSchedule()) {

                if (schedule.getDate().after(Date.valueOf(LocalDate.now().plusDays(1)))
                        && schedule.getDate().before(Date.valueOf(LocalDate.now().plusDays(8)))) {
                    listShiftsBookedNextSevenDay.add(shiftRepository.findById(schedule.getShiftId()).get());
                }
            }

            if (listShifts.size() > listShiftsBookedNextSevenDay.size()) {
                listDoctorNextSevenDay.add(doctor);
            }
        }
        return listDoctorNextSevenDay;
    }

    public List<DoctorRespone> searchByNameDoctor(List<DoctorRespone> listDoctor, String name) {
        List<DoctorRespone> resuList = new ArrayList<>();
        if (name == null || name == "") {
            return listDoctor;
        }

        for (DoctorRespone doctorRespone : listDoctor) {
            if (doctorRespone.getNameDoctor().toLowerCase().contains(name.toLowerCase())) {
                resuList.add(doctorRespone);
            }
        }
        return resuList;
    }

    public boolean checkFilterDay(Date date, String type) {
        switch (type) {
            case "today":
                if (date.equals(Date.valueOf(LocalDate.now())))
                    return true;
                break;

            case "tomorrow":
                if (date.equals(Date.valueOf(LocalDate.now().plusDays(1))))
                    return true;
                break;

            case "nextseven":
                if (date.after(Date.valueOf(LocalDate.now().plusDays(1)))
                        && date.before(Date.valueOf(LocalDate.now().plusDays(8))))
                    return true;
                break;

            default:
                if (date.equals(Date.valueOf(LocalDate.now()))
                        || date.after(Date.valueOf(LocalDate.now())))
                    return true;
        }
        return false;
    }

    public List<DoctorRespone> filterDoctorByGenderAndShift(List<DoctorRespone> listDoctor, Boolean gender,
            String _Shift, String type) {
        if (gender == null && _Shift.equals("")) {
            return listDoctor;
        }
        List<Shift> listAllShifts = shiftServices.getShiftList();
        List<DoctorRespone> resuList = new ArrayList<>();

        for (DoctorRespone doctorRespone : listDoctor) {
            List<Shift> listShiftBooked = new ArrayList<>();
            List<Shift> listShiftAvailable = new ArrayList<>();

            if (gender == null) {
                if (_Shift.equals("morning")) {
                    if (type.equals("none")) {
                        resuList.add(doctorRespone);
                    } else {
                        for (ScheduleRespone scheduleRespone : doctorRespone.getListSchedule()) {
                            if (checkFilterDay(scheduleRespone.getDate(), type)) {
                                Shift shiftofSchedule = shiftRepository.findById(scheduleRespone.getShiftId()).get();
                                listShiftBooked.add(shiftofSchedule);
                            }
                        }
                        for (Shift shift : listAllShifts) {
                            if (shift.getId() <= 7 && !listShiftBooked.contains(shift)) {
                                listShiftAvailable.add(shift);
                            }
                        }
                        if (listShiftAvailable.size() > 0)
                            resuList.add(doctorRespone);
                    }
                } else if (_Shift.equals("afternoon")) {
                    if (type.equals("none")) {
                        resuList.add(doctorRespone);
                    } else {
                        for (ScheduleRespone scheduleRespone : doctorRespone.getListSchedule()) {
                            if (checkFilterDay(scheduleRespone.getDate(), type)) {
                                Shift shiftofSchedule = shiftRepository.findById(scheduleRespone.getShiftId()).get();
                                listShiftBooked.add(shiftofSchedule);
                            }
                        }
                        for (Shift shift : listAllShifts) {
                            if (shift.getId() > 7 && !listShiftBooked.contains(shift)) {
                                listShiftAvailable.add(shift);
                            }
                        }
                        if (listShiftAvailable.size() > 0)
                            resuList.add(doctorRespone);
                    }
                } else {
                    resuList.add(doctorRespone);
                }
            }

            if (doctorRespone.getGender().equals(gender)) {
                if (_Shift.equals("morning")) {
                    if (type.equals("none")) {
                        resuList.add(doctorRespone);
                    } else {
                        for (ScheduleRespone scheduleRespone : doctorRespone.getListSchedule()) {
                            if (checkFilterDay(scheduleRespone.getDate(), type)) {
                                Shift shiftofSchedule = shiftRepository.findById(scheduleRespone.getShiftId()).get();
                                listShiftBooked.add(shiftofSchedule);
                            }
                        }
                        for (Shift shift : listAllShifts) {
                            if (shift.getId() <= 7 && !listShiftBooked.contains(shift)) {
                                listShiftAvailable.add(shift);
                            }
                        }
                        if (listShiftAvailable.size() > 0)
                            resuList.add(doctorRespone);
                    }
                } else if (_Shift.equals("afternoon")) {
                    if (type.equals("none")) {
                        resuList.add(doctorRespone);
                    } else {
                        for (ScheduleRespone scheduleRespone : doctorRespone.getListSchedule()) {
                            if (checkFilterDay(scheduleRespone.getDate(), type)) {
                                Shift shiftofSchedule = shiftRepository.findById(scheduleRespone.getShiftId()).get();
                                listShiftBooked.add(shiftofSchedule);
                            }
                        }
                        for (Shift shift : listAllShifts) {
                            if (shift.getId() > 7 && !listShiftBooked.contains(shift)) {
                                listShiftAvailable.add(shift);
                            }
                        }
                        if (listShiftAvailable.size() > 0)
                            resuList.add(doctorRespone);
                    }
                } else {
                    resuList.add(doctorRespone);
                }
            }
        }
        return resuList;
    }
}