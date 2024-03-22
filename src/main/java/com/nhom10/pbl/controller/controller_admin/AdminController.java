package com.nhom10.pbl.controller.controller_admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @RequestMapping("/admin")
    public String admin() {
        return "admin/index";
    }
}
