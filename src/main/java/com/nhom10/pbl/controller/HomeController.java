package com.nhom10.pbl.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhom10.pbl.models.ERole;
import com.nhom10.pbl.payload.response.DepartmentRespone;
import com.nhom10.pbl.payload.response.UserResponse;
import com.nhom10.pbl.services.DepartmentServices;
import com.nhom10.pbl.security.service.AuthenticateService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {

    private final DepartmentServices departmentServices;
    private final AuthenticateService authenticateService;

    @GetMapping("/home")
    public String getHomePage(Model model, HttpServletRequest request) {

        UserResponse user = authenticateService.getUserFromCookie(request);

        model.addAttribute("view", "homePage/homeComponent/homePage");
        model.addAttribute("file", "homePage");
        model.addAttribute("nav", "homePage/partials/navLogged");
        model.addAttribute("navState", "navLogged");

        if (user.getUsername() != null) {

            if (user.getRole().equals(ERole.DOCTOR.name())) {
                model.addAttribute("nav", "homePage/partials/navDoctorLogged");
                model.addAttribute("navState", "navDoctorLogged");
            } else if (user.getRole().equals(ERole.PATIENT.name())) {
                model.addAttribute("nav", "homePage/partials/navLogged");
                model.addAttribute("navState", "navLogged");
                List<DepartmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
                model.addAttribute("listDepartmentRespones", listDepartmentRespones);
            }
            model.addAttribute("user", user);
        }

        return "homePage/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "auth/login/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "homePage/index";
    }

    @RequestMapping("/admin")
    public String adminPage(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/chart";
    }

    @RequestMapping("/admin/accounts")
    public String adminControllUsers(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/accounts";
    }

    @RequestMapping("/admin/articles")
    public String adminControllArticles(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/articles";
    }

    @RequestMapping("/admin/departments")
    public String adminControllDepartments(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/departments";
    }

    @RequestMapping("/admin/doctors")
    public String adminControllDoctors(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/doctors";
    }

    @RequestMapping("/admin/schedules")
    public String adminControllSchedules(Model model, HttpServletRequest request) {
        UserResponse user = authenticateService.getUserFromCookie(request);
        model.addAttribute("user", user);
        return "admin/pages/schedules";
    }
}
