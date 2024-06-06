package com.nhom10.pbl.payload.resquest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordCheckRequest {
    String inputPassword;
    String encodedPassword;
}
