package com.nhom10.pbl.security.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nhom10.pbl.models.Role;
import com.nhom10.pbl.models.UserModel;

public class CustomUserDetails implements UserDetails {
    private Collection<? extends GrantedAuthority> authorities;
    private UserModel user;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private boolean credentialsNonExpired;

    public CustomUserDetails() {
    }

    public CustomUserDetails(Collection<? extends GrantedAuthority> authorities, UserModel user,
            Boolean accountNonExpired,
            Boolean accountNonLocked, boolean credentialsNonExpired) {
        this.authorities = authorities;
        this.user = user;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public static CustomUserDetails build(UserModel user) {
        // collection of authorities of the user
        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        Set<Role> roles = user.getRoles();

        for (Role role : roles) {
            // foreach userRole of the user, add that role to authorities
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }

        return new CustomUserDetails(authorities, user, true, true, true);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassWord();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

}
