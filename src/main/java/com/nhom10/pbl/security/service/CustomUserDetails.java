package com.nhom10.pbl.security.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nhom10.pbl.models.ERole;
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
        Role role = user.getRole();

        ERole roleName = role.getName();

        authorities.add(new SimpleGrantedAuthority(roleName.name()));

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
