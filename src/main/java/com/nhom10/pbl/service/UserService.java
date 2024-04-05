package com.nhom10.pbl.service;
import com.nhom10.pbl.models.UserModel;


import com.nhom10.pbl.Dto.UserDto;

public interface  UserService {
    
    public UserModel save(UserDto userDto);
}