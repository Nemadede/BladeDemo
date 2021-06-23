package com.bladeDemo.controller.session.utils;

import com.bladeDemo.commons.models.User;
import com.bladeDemo.controller.session.params.LoginResponse;
import com.bladeDemo.controller.session.params.TokenParams;
import com.bladeDemo.controller.session.params.UserResponseParams;
import com.bladeDemo.utils.ResponseBuilder;

import java.util.HashMap;
import java.util.Map;

public class SessionResponseBuilder extends ResponseBuilder {



    public Object userJson(User user){
        return UserResponseParams
                    .builder()
                    .id(user.getId())
                    .full_name(null)
                    .email(user.getEmail())
                    .build();
    }

    public static Object tokenPayload(User user){
        return TokenParams.builder()
                .email(user.getEmail())
                .build();
    }

    public Object toSignUp(Object data, String message, Boolean success){
        Map<String, Object> dataResponse = new HashMap<>();
        if(data != null) {
            data = userJson((User) data);
        }
        dataResponse.put("data", data);
        dataResponse.put("success", success);
        dataResponse.put("message", message);

        return dataResponse;
    }


    public Object loginResponse(Object data, Map<String, String> tokens){
        Map<String, Object> dataResponse = new HashMap<>();

        Object response = LoginResponse
                .builder()
                .token((String)tokens.get("access-token"))
                .token_type((String) "Authorization")
                .ref_token((String) tokens.get("refresh-token"))
                .build();

        dataResponse.put("data", response);
        dataResponse.put("success", true);
        dataResponse.put("message", "Login successful");

        return dataResponse;
    }


}
