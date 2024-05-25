package com.nhom10.pbl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhom10.pbl.Dto.UserDto;
import com.nhom10.pbl.service.UserServiceImpl;



@Controller
public class UserController {
   @Autowired 
   private UserServiceImpl userService;
   // @GetMapping("/user/login")
   // public String get_register(){
   //    return "register";
   // }
   @PostMapping("/register")
   public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
      // System.out.println(userDto.getMail()+" "+userDto.getName() +" "+userDto.getUsername());
        userService.save(userDto);
      //   model.addAttribute("message", "Đăng ký thành công");
      return ResponseEntity.ok(userDto);
   }
   @GetMapping("/")
   public String home(){
      return "index";
   }
	// @GetMapping("/edit/{id}")
	// public String Edit(@PathVariable Long id, @RequestBody UserModel userdetails) {
		// UserModel user = userService.getbyId(id)
      // // .orElseThrow(()-> new ResourceNotFoundException("not exist with id: " + id));
		// user.setAddress(userdetails.getAddress());
      // user.setBirthday(userdetails.getBirthday());
      // user.setEmail(userdetails.getEmail());
      // user.setFullName(userdetails.getFullName());
      // user.setGender(userdetails.getGender());
      // user.setPassWord(userdetails.getPassWord());
      // user.setTelephone(userdetails.getTelephone());
      // user.setUserName(userdetails.getUserName());

      // userService.save(user);
		// return "edit";
	}
   //   @PutMapping("{id}")
   //  public ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails) {
   //      Employee updateEmployee = employeeRepository.findById(id)
   //              .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

   //      updateEmployee.setFirstName(employeeDetails.getFirstName());
   //      updateEmployee.setLastName(employeeDetails.getLastName());
   //      updateEmployee.setEmailId(employeeDetails.getEmailId());

   //      employeeRepository.save(updateEmployee);

   //      return ResponseEntity.ok(updateEmployee);
   //  }