package com.nhom10.pbl.payload.request;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    // register request does not need id and enabled
    private String username;
    private String password;
    private String fullname;
    private Boolean gender;
    private Date birthday;
    private String email;
    private String phone;
    private String role;
}
