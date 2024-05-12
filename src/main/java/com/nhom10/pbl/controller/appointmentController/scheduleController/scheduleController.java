package com.nhom10.pbl.controller.appointmentController.scheduleController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom10.pbl.payload.response.scheduleRespone;
import com.nhom10.pbl.payload.resquest.scheduleRequest;
import com.nhom10.pbl.services.scheduleServices;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class scheduleController {
    @Autowired
    private scheduleServices scheduleServices;

    @PostMapping(path = "/appointment", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public String createSchedulejson(scheduleRequest sRequest, HttpServletRequest request) {
        scheduleServices.createSchedule(sRequest);
        String prevUrl = request.getHeader("referer");
        return "redirect:" + prevUrl;
    }

    @GetMapping("/appointment")
    @ResponseBody
    public List<scheduleRespone> getListOfSchedules() {
        return scheduleServices.getAllschedule();
    }
}
