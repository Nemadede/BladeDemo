package com.bladeDemo.commons.params;

import lombok.Builder;

@Builder
public class ErrorResponse {
    Boolean success;
    String error;
}
