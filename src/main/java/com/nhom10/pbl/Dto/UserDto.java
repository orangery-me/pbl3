package com.nhom10.pbl.Dto;

import java.sql.Date;

public class UserDto  {
    private String  username;
    private String fullname;
    private boolean gender;
    private Date birthday;
    private  String password;
    private String email;
    
  
   
    public UserDto(String username, String fullname, boolean gender, Date birthday, String email, String password) {
        this.username = username;
        this.fullname = fullname;
        this.gender = gender;
        this.birthday = birthday;
        // this.address = address;
        this.password = password;
        this.email = email;
    }
    public String getMail() {
        return email;
    }
    public void setMail(String mail) {
        this.email = mail;
    }
    public String getUsername() {
        return username;
    }
    public String getName() {
        return fullname;
    }
    public boolean getGender() {
        return gender;
    }
    public Date getYear() {
        return birthday;
    }
    // public String getAddress() {
    //     return address;
    // }
    public String getPass() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setName(String name) {
        this.fullname = name;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public void setYear(Date birthday) {
        this.birthday =birthday;
    }
    // public void setAddress(String address) {
    //     this.address = address;
    // }
    public void setPass(String pass) {
        this.password = pass;
    }
    
}
