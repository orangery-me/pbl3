package com.nhom10.pbl.controller.controller_admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @RequestMapping("/admin/user")
    public String userController() {
        return "admin/user_controller/index";
    }
}
