package com.nhom10.pbl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhom10.pbl.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    public UserModel findByUserName(String userName);

}
