package com.bladeDemo.controller.session.params;

import lombok.Data;

@Data
public class PasswordResetParams {
    String secret;
    String new_password;
}
