package com.nhom10.pbl.service;
import com.nhom10.pbl.models.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.Dto.UserDto;
import com.nhom10.pbl.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository; 
    @Override
    public UserModel save(UserDto userDto) {
         UserModel userModel = new UserModel();
        userModel.setEmail(userDto.getMail());
        userModel.setPassWord(userDto.getPass());
        userModel.setUserName(userDto.getUsername());
        userModel.setFullName(userDto.getName());
        userModel.setGender(userDto.getGender());
        userModel.setBirthday(userDto.getYear());
        return userRepository.save(userModel);
    }

}