package com.nhom10.pbl.models;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "address")
    private String address;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String telephone;
	@JoinColumn(name = "roleId",referencedColumnName = "id")
	private String role;
    public UserModel() {
		super();
	}
    public UserModel(Long id, String userName, String fullName,boolean gender, Date birthday, String address, String passWord,
            String email, String telephone, String role) {
        this.id = id;
        this.username = userName;
        this.password = passWord;
        this.fullname = fullName;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.role = role;
    }
    public Long getId() {
        return id;
    }
    public String getUserName() {
        return username;
    }
    public String getPassWord() {
        return password;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public String getFullName() {
        return fullname;
    }
    public boolean getGender() {
        return gender;
    }
    public Date getBirthday() {
        return birthday;
    }
    public String getAddress() {
        return address;
    }
    public String getEmail() {
        return email;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getRole() {
        return role;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUserName(String userName) {
        this.username = userName;
    }
    public void setPassWord(String passWord) {
        this.password = passWord;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public void setFullName(String fullName) {
        this.fullname = fullName;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
}