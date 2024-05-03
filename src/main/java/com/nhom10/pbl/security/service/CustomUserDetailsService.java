package com.nhom10.pbl.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = repository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return CustomUserDetails.build(user);
    }

}
