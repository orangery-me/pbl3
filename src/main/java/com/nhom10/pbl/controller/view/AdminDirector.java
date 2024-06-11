package com.nhom10.pbl.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhom10.pbl.payload.response.UserResponse;
import com.nhom10.pbl.security.service.AuthenticateService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminDirector {
    private final AuthenticateService authenticateService;

    @RequestMapping("")
    public String adminPage(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/chart";
    }

    @RequestMapping("/accounts")
    public String adminControllUsers(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/accounts";
    }

    @RequestMapping("/articles")
    public String adminControllArticles(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/articles";
    }

    @RequestMapping("/departments")
    public String adminControllDepartments(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/departments";
    }

    @RequestMapping("/doctors")
    public String adminControllDoctors(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/doctors";
    }

    @RequestMapping("/schedules")
    public String adminControllSchedules(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/schedules";
    }
}
