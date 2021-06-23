package com.bladeDemo.controller.session.params;

import lombok.Builder;

import java.util.Date;

@Builder
public class LoginResponse {
    private String token;
    private String token_type ;
    private String ref_token;

}
