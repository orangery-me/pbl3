package com.nhom10.pbl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/home/greeting")
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok("Greeting from home");
    }

    @RequestMapping("/login")
    public String login() {
        return "auth/login/login";
    }
}
