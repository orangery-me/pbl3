package com.nhom10.pbl.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhom10.pbl.payload.response.departmentRespone;
import com.nhom10.pbl.security.jwt.JWTService;
import com.nhom10.pbl.services.departmentServices;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final departmentServices departmentServices;
    private final JWTService jwtService;

    @GetMapping("/home")
    public String getHomePage(Model model, HttpServletRequest request) {
        String username = "thi";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    username = jwtService.extractUserName(cookie.getValue());
                }
            }
        }
        model.addAttribute("username", username);
        if (username == null) {
            model.addAttribute("view", "homePage/homeComponent/homePage");
            model.addAttribute("file", "homePage");
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
        } else {
            List<departmentRespone> listDepartmentRespones = departmentServices.getAllDepartmentRespones();
            model.addAttribute("view", "homePage/homeComponent/homePage");
            model.addAttribute("file", "homePage");
            model.addAttribute("nav", "homePage/partials/navLogged");
            model.addAttribute("navState", "navLogged");
            model.addAttribute("listDepartmentRespones", listDepartmentRespones);
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
    public String adminPage() {
        return "admin/pages/home";
    }

    @RequestMapping("/admin/accounts")
    public String adminControllUsers() {
        return "admin/pages/accounts";
    }

    @RequestMapping("/admin/articles")
    public String adminControllArticles() {
        return "admin/pages/articles";
    }
}
