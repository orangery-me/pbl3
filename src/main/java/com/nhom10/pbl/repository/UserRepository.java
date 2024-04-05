package com.nhom10.pbl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhom10.pbl.models.UserModel;

import org.springframework.stereotype.Repository;
@Repository

public interface UserRepository  extends JpaRepository<UserModel, Long> {
    // UserModel findUserByEmail(String email);
}
