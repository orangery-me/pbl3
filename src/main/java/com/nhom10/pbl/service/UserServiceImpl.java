package com.nhom10.pbl.service;
import com.nhom10.pbl.models.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.Dto.UserDto;
import com.nhom10.pbl.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository; 
    
    public UserModel save(UserDto userDto) {
        // System.out.println(userDto.getName()+" "+userDto.getMail()+ " " + userDto.getPass()+ " " + userDto.getYear());
         UserModel userModel = new UserModel();
        userModel.setEmail(userDto.getMail());
        userModel.setPassWord(userDto.getPass());
        userModel.setUserName(userDto.getUsername());
        userModel.setFullName(userDto.getName());
        userModel.setGender(userDto.getGender());
        userModel.setBirthday(userDto.getYear());
        return userRepository.save(userModel);
    }
   
    public UserModel getbyId(Long id) {
        return userRepository.findById(id).get();
    }


  

}