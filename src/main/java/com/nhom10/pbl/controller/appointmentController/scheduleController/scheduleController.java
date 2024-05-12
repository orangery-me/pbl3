package com.nhom10.pbl.controller.appointmentController.scheduleController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom10.pbl.dto.request.scheduleRequest;
import com.nhom10.pbl.dto.respone.bookingModel;
import com.nhom10.pbl.dto.respone.doctorRespone;
import com.nhom10.pbl.dto.respone.scheduleRespone;
import com.nhom10.pbl.models.schedule;
import com.nhom10.pbl.services.doctorServices;
import com.nhom10.pbl.services.scheduleServices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class scheduleController {

    private final scheduleServices scheduleServices;
    private final doctorServices _doctorServices;

    @PostMapping(path = "/appointment/{doctorId}")
    public String createSchedulejson(@PathVariable("doctorId") String doctorId,
            scheduleRequest sRequest, HttpServletRequest request, Model model) {
        scheduleServices.createSchedule(sRequest);

        List<bookingModel> ListBookingAvailable = _doctorServices
                .getListBookingModelsOfDoctor(Long.parseLong(doctorId));
        doctorRespone doctorRespone = _doctorServices.getDoctorResponeById(Long.parseLong(doctorId));

        model.addAttribute("doctor", doctorRespone);
        model.addAttribute("ListbookingAvailable", ListBookingAvailable);

        return "homePage/homeComponent/bookingComponent";
    }

    @GetMapping("/appointment")
    @ResponseBody
    public List<scheduleRespone> getListOfSchedules() {
        return scheduleServices.getAllschedule();
    }

    // @GetMapping("/appointments")
    // @ResponseBody
    // public Long getListSchedulesToday(){

    // return scheduleServices.getRevenueOfDay(Date.valueOf(LocalDate.now()));
    // }
}
