package com.nhom10.pbl.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhom10.pbl.dto.respone.departmentRespone;
import com.nhom10.pbl.services.departmentServices;

@Controller
public class HomeController {

    private final departmentServices _departmentServices;

    public HomeController(departmentServices departmentServices) 
    {
        this._departmentServices = departmentServices;
    }

    @RequestMapping("")
    public String home() {
        return "index";
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        List<departmentRespone> listDepartmentRespones =  _departmentServices.getAllDepartmentRespones();
        model.addAttribute("view", "homePage/homeComponent/homePage");
        model.addAttribute("file", "homePage");
        model.addAttribute("nav", "homePage/partials/navLogged");
        model.addAttribute("navState", "navLogged");
        model.addAttribute("listDepartmentRespones", listDepartmentRespones);
        return "homePage/index";
    }

    @GetMapping("/home/greeting")
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok("Greeting from home");
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
}
