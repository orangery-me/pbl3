package com.nhom10.pbl.controller.appointment_controller.schedule_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom10.pbl.payload.response.ScheduleRespone;
import com.nhom10.pbl.payload.request.ScheduleRequest;
import com.nhom10.pbl.services.ScheduleServices;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class ScheduleController {
    @Autowired
    private ScheduleServices scheduleServices;

    @PostMapping(path = "/appointment", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public String createSchedulejson(ScheduleRequest sRequest, HttpServletRequest request) {
        try {
            scheduleServices.createSchedule(sRequest);
            String prevUrl = request.getHeader("referer");
            return "redirect:" + prevUrl;
        } catch (Exception e) {
            System.out.println(e);
            return "redirect:/error";
        }
    }

    @GetMapping("/appointment")
    @ResponseBody
    public List<ScheduleRespone> getListOfSchedules() {
        return scheduleServices.getAllschedules();
    }
}
