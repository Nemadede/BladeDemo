package com.bladeDemo.controller.session.params;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginParams {
    public String password;
    public String email;

}
