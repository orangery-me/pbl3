package com.nhom10.pbl.controller.appointmentController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom10.pbl.payload.response.BookingModel;
import com.nhom10.pbl.payload.response.DoctorResponse;
import com.nhom10.pbl.payload.response.ScheduleRespone;
import com.nhom10.pbl.payload.request.ScheduleRequest;
import com.nhom10.pbl.services.DoctorServices;
import com.nhom10.pbl.services.ScheduleServices;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class ScheduleController {
    @Autowired
    private ScheduleServices scheduleServices;
    @Autowired
    private DoctorServices _doctorServices;

    @PostMapping(path = "/appointment/{doctorId}")
    public String createSchedulejson(@PathVariable("doctorId") String doctorId,
            ScheduleRequest sRequest, HttpServletRequest request, Model model) {
        scheduleServices.createSchedule(sRequest);

        List<BookingModel> ListBookingAvailable = _doctorServices
                .getListBookingModelsOfDoctor(Long.parseLong(doctorId));
        DoctorResponse doctorRespone = _doctorServices.getDoctorResponeById(Long.parseLong(doctorId));

        model.addAttribute("doctor", doctorRespone);
        model.addAttribute("ListbookingAvailable", ListBookingAvailable);

        return "homePage/homeComponent/bookingComponent";
    }

    @GetMapping("/appointment")
    @ResponseBody
    public List<ScheduleRespone> getListOfSchedules() {
        return scheduleServices.getAllschedules();
    }
}
