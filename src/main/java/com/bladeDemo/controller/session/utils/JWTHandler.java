package com.bladeDemo.controller.session.utils;

import com.bladeDemo.utils.DateUtils;
import com.bladeDemo.utils.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class JWTHandler extends DateUtils {

    public static String getTokenString(String payload, String key, Date expirationDate){
        // build token string with necessary parameters
        return Jwts.builder().setSubject(payload)
                .signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(expirationDate)
                .setIssuedAt(new Date()).setId("v2")
                .setIssuer("nema-dede")
                .compact();
    }

    public static boolean isValid(String token, String key){
        try{
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static String refreshToken(){
        return "";
    }

    public static Claims getClaims(String token, String key){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);

        return claimsJws.getBody();
    }

    public static Map<String, String> getClaimData(String token, String key){
        Claims claimsJws = getClaims(token,key);

        Map<String, String> jwtData = JSON.parseToMap(Objects.requireNonNull(claimsJws).getSubject());

        return jwtData;
    }

    public static long getExpirations(String token, String key){
        Claims claimsJws = getClaims(token,key);
        System.out.println(claimsJws.getExpiration());
        return claimsJws.getExpiration().getTime();
    }
}
