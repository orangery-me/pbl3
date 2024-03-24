package com.nhom10.pbl.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.MyUserDetails;
import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.models.UserRole;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userService.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // collection of authorities of the user
        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        Set<UserRole> roles = user.getRoleUsers();

        for (UserRole userRole : roles) {
            // foreach userRole of the user, add that role to authorities
            authorities.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
        }
        return new MyUserDetails(authorities, user, true, true, true);
    }

}
