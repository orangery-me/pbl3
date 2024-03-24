package com.nhom10.pbl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
