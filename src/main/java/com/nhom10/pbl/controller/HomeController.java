package com.nhom10.pbl.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhom10.pbl.payload.response.departmentRespone;
import com.nhom10.pbl.services.departmentServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final departmentServices _departmentServices;

    public HomeController(departmentServices departmentServices) {
        this._departmentServices = departmentServices;
    }

    @GetMapping("/home")
    public String getHomePage(Model model, HttpSession session) {
        var user = session.getAttribute("user");
        if (user == null) {
            model.addAttribute("view", "homePage/homeComponent/homePage");
            model.addAttribute("file", "homePage");
            model.addAttribute("nav", "homePage/partials/nav");
            model.addAttribute("navState", "nav");
        } else {
            List<departmentRespone> listDepartmentRespones = _departmentServices.getAllDepartmentRespones();
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
