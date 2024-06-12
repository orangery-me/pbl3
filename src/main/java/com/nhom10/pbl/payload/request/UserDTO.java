package com.nhom10.pbl.payload.request;

import java.sql.Date;

import com.nhom10.pbl.models.ERole;
import com.nhom10.pbl.models.Role;
import com.nhom10.pbl.models.UserModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String fullname;
    private boolean gender;
    private Date birthday;
    private boolean enabled;
    private String role;

    public static UserDTO mapToUserDTO(UserModel user) {

        return UserDTO.builder()
                .username(user.getUserName())
                .password(user.getPassWord())
                .email(user.getEmail())
                .phone(user.getTelephone())
                .fullname(user.getFullname())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .enabled(user.getEnabled())
                .role(user.getRole().getName().name())
                .build();
    }

    public static UserModel mapToUserModel(UserDTO user) {
        return UserModel.builder()
                .userName(user.getUsername())
                .passWord(user.getPassword())
                .email(user.getEmail())
                .telephone(user.getPhone())
                .fullname(user.getFullname())
                .gender(user.isGender())
                .birthday(user.getBirthday())
                .enabled(user.isEnabled())
                .role(stringToRole(user.getRole()))
                .build();
    }

    public static Role stringToRole(String strRole) {
        final Role role;

        if (strRole == null) {
            Role patient = new Role();
            patient.setName(ERole.PATIENT);
            role = patient;
        } else {

            switch (strRole) {
                case "ADMIN":
                    Role admin = new Role();
                    admin.setName(ERole.ADMIN);
                    role = admin;

                    break;
                case "DOCTOR":
                    Role doctor = new Role();
                    doctor.setName(ERole.DOCTOR);
                    role = doctor;

                    break;
                default:
                    Role patient = new Role();
                    patient.setName(ERole.PATIENT);
                    role = patient;
            }
        }

        return role;
    }
}
