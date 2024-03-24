package com.nhom10.pbl.service;

import com.nhom10.pbl.models.UserModel;

public interface UserService {
    public UserModel findByUserName(String userName);
}
