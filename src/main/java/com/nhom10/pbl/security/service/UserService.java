package com.nhom10.pbl.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.payload.response.UserResponse;
import com.nhom10.pbl.payload.resquest.UserDTO;
import com.nhom10.pbl.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<UserResponse> getAllUsers() {
        return repository.findAll().stream()
                .map(UserResponse::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) throws Exception {
        UserModel user = repository.getUserById(id)
                .orElseThrow(() -> new Exception("User not found"));
        return UserResponse.mapToUserResponse(user);
    }

    public UserResponse updateUser(Long id, UserDTO user) throws Exception {
        UserModel userFromDb = repository.getUserById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        try {
            userFromDb.setPassWord(user.getPassword());
            userFromDb.setFullName(user.getFullname());
            userFromDb.setEmail(user.getEmail());
            userFromDb.setTelephone(user.getPhone());
            userFromDb.setBirthday(user.getBirthday());
            userFromDb.setEnabled(user.isEnabled());
            userFromDb.setGender(user.isGender());
            repository.save(userFromDb);
            return UserResponse.mapToUserResponse(userFromDb);
        } catch (Exception e) {
            throw new Exception("Update user failed");
        }

    }

    public void deleteUser(Long id) throws Exception {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Delete user failed");
        }
    }
}
