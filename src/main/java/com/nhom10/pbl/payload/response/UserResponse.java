package com.nhom10.pbl.payload.response;

import com.nhom10.pbl.models.UserModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String fullname;
    private boolean gender;
    private String birthday;
    private boolean enabled;
    private String role;

    public static UserResponse mapToUserResponse(UserModel user) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUserName())
                .password(user.getPassWord())
                .email(user.getEmail())
                .phone(user.getTelephone())
                .fullname(user.getFullName())
                .gender(user.getGender())
                .birthday(user.getBirthday().toString())
                .enabled(user.getEnabled())
                .role(user.getRole().getName().name())
                .build();
    }
}
