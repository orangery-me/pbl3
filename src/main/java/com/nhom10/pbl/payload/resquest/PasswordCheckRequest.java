package com.nhom10.pbl.payload.resquest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordCheckRequest {
    String inputPassword;
    String encodedPassword;
}
