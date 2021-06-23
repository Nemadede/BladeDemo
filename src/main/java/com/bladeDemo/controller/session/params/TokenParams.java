package com.bladeDemo.controller.session.params;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenParams {
    private String email;
}
