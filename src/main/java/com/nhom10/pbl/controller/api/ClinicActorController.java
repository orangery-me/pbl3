package com.nhom10.pbl.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhom10.pbl.models.Shift;
import com.nhom10.pbl.payload.response.DepartmentRespone;
import com.nhom10.pbl.payload.response.DoctorInfoResponse;
import com.nhom10.pbl.payload.response.DoctorResponse;
import com.nhom10.pbl.payload.response.ScheduleInfoResponse;
import com.nhom10.pbl.payload.request.DepartmentRequest;
import com.nhom10.pbl.services.DepartmentServices;
import com.nhom10.pbl.services.DoctorServices;
import com.nhom10.pbl.services.ScheduleServices;
import com.nhom10.pbl.services.ShiftServices;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("api/clinic")
public class ClinicActorController {
    @Autowired
    private DepartmentServices _departmentServices;

    @Autowired
    private DoctorServices _doctorServices;

    @Autowired
    private ScheduleServices scheduleServices;

    @Autowired
    private ShiftServices shiftServices;

    @PostMapping("/departments/newDepartment")
    public ResponseEntity<DepartmentRespone> newDepartment(@RequestBody DepartmentRequest departmentRequest) {
        try {
            return ResponseEntity.ok(_departmentServices.createDepartment(departmentRequest));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/departments/all")
    public ResponseEntity<List<DepartmentRespone>> getAllDep() {
        try {
            return ResponseEntity.ok(_departmentServices.getAllDepartmentRespones());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    // @PostMapping("/doctors/newDoctor")
    // public ResponseEntity<DoctorInfoResponse> newDoctor(@RequestBody
    // DoctorRequest doctorRequest) {
    // try {
    // return ResponseEntity.ok(_doctorServices.createNewDoctor(doctorRequest));
    // } catch (Exception e) {
    // System.out.println(e);
    // return ResponseEntity.badRequest().build();
    // }
    // }

    @GetMapping("/doctors/all")
    public ResponseEntity<List<DoctorInfoResponse>> getAllDoctors() {
        try {
            return ResponseEntity.ok(_doctorServices.getAllDoctors());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponse>> getListDoctorOfDepartment(@RequestParam String departmentId,
            Model model) {
        try {
            return ResponseEntity.ok(_departmentServices.getListDoctor(Long.parseLong(departmentId), false));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/schedules/all")
    public ResponseEntity<List<ScheduleInfoResponse>> getAllSchedulesInfo() {
        try {
            return ResponseEntity.ok(scheduleServices.getAllScheduleInfo());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/shifts/all")
    public ResponseEntity<List<Shift>> getAllShifts() {
        try {
            return ResponseEntity.ok(shiftServices.getShiftList());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

}
