package com.nhom10.pbl.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.payload.response.UserResponse;
import com.nhom10.pbl.payload.request.UserDTO;
import com.nhom10.pbl.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponse> getUserById(Long id) throws Exception {
        UserModel user = repository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));
        return new ArrayList<UserResponse>() {
            {
                add(UserResponse.mapToUserResponse(user));
            }
        };
    }

    public List<UserResponse> getUserByUsername(String username) throws Exception {
        List<UserResponse> responses = new ArrayList<UserResponse>();
        repository.findByUserNameContaining(username).ifPresent(users -> {
            responses.addAll(users.stream().map(UserResponse::mapToUserResponse).collect(Collectors.toList()));
        });
        return responses;
    }

    public List<UserResponse> getUserByEmail(String email) throws Exception {
        List<UserResponse> responses = new ArrayList<UserResponse>();
        repository.findByEmailContaining(email).ifPresent(users -> {
            responses.addAll(users.stream().map(UserResponse::mapToUserResponse).collect(Collectors.toList()));
        });
        return responses;
    }

    public List<UserResponse> getUserByPhone(String phone) throws Exception {
        List<UserResponse> responses = new ArrayList<UserResponse>();
        repository.findByTelephoneContaining(phone).ifPresent(users -> {
            responses.addAll(users.stream().map(UserResponse::mapToUserResponse).collect(Collectors.toList()));
        });
        System.out.println(responses);
        return responses;
    }

    public List<UserResponse> getAllUsers() {
        return repository.findAll().stream()
                .map(UserResponse::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(Long id, UserDTO user) throws Exception {
        UserModel userFromDb = repository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        try {
            userFromDb.setPassWord(passwordEncoder.encode(user.getPassword()));
            userFromDb.setFullname(user.getFullname());
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
