package com.bladeDemo.utils;

import com.bladeDemo.utils.configs.GlobalConfigs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SecretKeyGenerator {
    private static GlobalConfigs configs = GlobalConfigs.getInstance();

    public static String getHMAC(String s){
        /*
        * HMac
         */
        String hash = null;


        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(configs.getKey().getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);

            hash = Base64.encodeBase64String(sha256_HMAC.doFinal(s.getBytes()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static String getSecretKey(String email){
        String s = Jwts.builder().setSubject(email)
                .signWith(SignatureAlgorithm.HS256, configs.getKey())
                .compact();
        String [] res = s.split("\\.");

        return res[0]+res[1]+res[2];
    }


}
