package com.bladeDemo.controller.session.utils;

import com.blade.kit.BCrypt;
import com.blade.mvc.http.Request;
import com.bladeDemo.commons.models.User;
import com.bladeDemo.commons.params.DataResponse;
import com.bladeDemo.utils.*;
import com.bladeDemo.utils.configs.GlobalConfigs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private static GlobalConfigs configs = GlobalConfigs.getInstance();


    public static Map<String, String> authenticate(User user){

        Map<String, String> map = new HashMap<>();

        String payload = JSON.pretty(SessionResponseBuilder.tokenPayload(user));

        Date tokenExDate = DateUtils.getExpiryDate(360);
        Date refreshTokenExDate = DateUtils.getExpiryDate(5000);

        String accessToken = JWTHandler.getTokenString(payload, configs.getKey(), tokenExDate);
        String refreshToken = JWTHandler.getTokenString(payload, configs.getKey(), refreshTokenExDate);

        map.put("access-token", accessToken);
        map.put("refresh-token", refreshToken);

        return map;
    }

    private static DataResponse buildResponse(boolean success, String message, Object payload) {
        return DataResponse
                .builder()
                .message(message)
                .data(payload)
                .success(success)
                .build();
    }

    public static DataResponse verifyToken(Request request){

        //Check if Authorization is in header
        if(!request.headers().containsKey("Authorization"))
            return buildResponse(false, "Unauthorized", null);

        //get the token String
        String token = request.header("Authorization");

        //Check if Token is valid
        if(!JWTHandler.isValid(token, configs.getKey()))
            return buildResponse(false, "Unauthorized", null);

        // Get the data
        Map<String, String> jwtData = JWTHandler.getClaimData(token,configs.getKey());

        return buildResponse(true, null, jwtData);
    }

    private static Map<String, String> returnToken(String email){
        User user = SessionService.getUserByEmail(email);
        return authenticate(user);
    }

    public static DataResponse refreshToken(String refresh_token){

        if(!JWTHandler.isValid(refresh_token, configs.getKey()))
            return buildResponse(false, "Unauthorized", null);

        Map<String, String> data = JWTHandler.getClaimData(refresh_token,configs.getKey() );
        Map<String, String> response = returnToken(data.get("email"));

        return buildResponse(true, null, response);
    }

    public static Map<String, String> hashPassword(String password){

        String salt = BCrypt.gensalt();
        String hashed = BCrypt.hashpw(password, salt);

        Map<String, String> res = new HashMap<>();
        res.put("salt", salt);
        res.put("pw_hash", hashed);

        return res;
    }



}
