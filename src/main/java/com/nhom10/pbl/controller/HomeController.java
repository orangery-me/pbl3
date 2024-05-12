package com.nhom10.pbl.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhom10.pbl.dto.respone.departmentRespone;
import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.security.jwt.JWTService;
import com.nhom10.pbl.services.departmentServices;
import com.nhom10.pbl.security.service.CustomUserDetails;
import com.nhom10.pbl.security.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {
    private final CustomUserDetailsService customUserDetailsService;
    private final departmentServices _departmentServices;
    private final JWTService jwtService;

    @GetMapping("/home")
    public String getHomePage(Model model, HttpServletRequest request) {

        String username = jwtService.extractUserNameFromTokenCookie(request);

        model.addAttribute("view", "homePage/homeComponent/homePage");
        model.addAttribute("file", "homePage");
        model.addAttribute("nav", "homePage/partials/navLogged");
        model.addAttribute("navState", "navLogged");

        if (username != null) {
            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UserModel currUser = userDetails.getUser();
            model.addAttribute("user", currUser);
            List<departmentRespone> listDepartmentRespones = _departmentServices.getAllDepartmentRespones();
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        }

        return "homePage/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "auth/login/login";
    }

    @RequestMapping("/admin")
    public String adminPage() {
        return "admin/pages/home";
    }

    @RequestMapping("/admin/accounts")
    public String adminControllUsers() {
        return "admin/pages/accounts";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "homePage/index";
    }

    @RequestMapping("/admin/articles")
    public String adminControllArticles() {
        return "admin/pages/articles";
    }} 

    
    