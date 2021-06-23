package com.bladeDemo.utils;

import com.bladeDemo.commons.params.ErrorResponse;
import lombok.Builder;

public class ResponseBuilder {

    public String toJson(Object o){
        return JSON.pretty(o);
    }


    @Builder
    public static class Error{
        public boolean success;
        public Object error;
    }

    @Builder
    public static class Message{
        public boolean success;
        public String message;
        public Object data;
    }

    @Builder
    public static class Email{
        public String email;
    }

    public String errorMessage(String mgs){
        return toJson(ErrorResponse.
                builder()
                .error(mgs)
                .success(false)
                .build());
    }

    public static Object messageResponse(boolean success, String message, Object data){
        return Message.builder()
                .success(success)
                .message(message)
                .data(data)
                .build();
    }
}
