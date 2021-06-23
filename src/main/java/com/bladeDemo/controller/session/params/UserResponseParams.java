package com.bladeDemo.controller.session.params;

import lombok.Builder;

@Builder
public class UserResponseParams {
    public Integer id;
    public String full_name;
    public String email;

}
