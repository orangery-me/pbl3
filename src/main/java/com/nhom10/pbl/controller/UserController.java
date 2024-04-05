package com.nhom10.pbl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nhom10.pbl.Dto.UserDto;
import com.nhom10.pbl.service.UserService;



@Controller
public class UserController {
   @Autowired 
   private UserService userService;
   @GetMapping("/register")
   public String get_register(){
      return "register";
   }
   @PostMapping("/register")
   public String saveUser(@ModelAttribute("user") UserDto userDto, Model model ){
      System.out.println("a");
        userService.save(userDto);
        model.addAttribute("message", "Đăng ký thành công");
      return "register";
   }
   @GetMapping("/")
   public String home(){
      return "index";
   }
	
}
