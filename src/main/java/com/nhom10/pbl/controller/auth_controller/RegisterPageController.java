package com.nhom10.pbl.controller.auth_controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterPageController {

    @GetMapping("/register")
    public String getRegisterPage() {
        return "auth/register/index";
    }
}
