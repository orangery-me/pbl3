package com.nhom10.pbl.Dto;

import java.sql.Date;

public class UserDto  {
    private String  username;
    private String name;
    private String gender;
    private Date birthday;
    private String address;
    private  String pass;
    private String mail;
    
  
   
    public UserDto(String username, String name, String gender, Date birthday, String address, String pass, String mail) {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.pass = pass;
        this.mail = mail;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getUsername() {
        return username;
    }
    public String getName() {
        return name;
    }
    public String getGender() {
        return gender;
    }
    public Date getYear() {
        return birthday;
    }
    public String getAddress() {
        return address;
    }
    public String getPass() {
        return pass;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setYear(Date birthday) {
        this.birthday =birthday;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    
}
