package com.bladeDemo.commons.params;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DataResponse {
    private Object data;
    private String message;
    private boolean success;

}
