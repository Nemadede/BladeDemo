package com.bladeDemo.connectors.utils;

import com.bladeDemo.utils.configs.Configs;
import com.bladeDemo.connectors.utils.AuthErrorException;
import com.typesafe.config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class HttpBuilder {
    private static Config conf = new Configs.Builder().withResource("dev.conf").build();
    private static Config qbConfig = conf.getConfig("app.qb");


    private static String httpResponse(HttpURLConnection connection) throws AuthErrorException {
        try {
            String inputLine;
            StringBuilder response = new StringBuilder();

            if (connection.getResponseCode() == 401) {
                throw new AuthErrorException("Status code " + connection.getResponseCode() + " ::: Token Expired");
            }

            if (connection.getResponseCode() > 299) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }

                bufferedReader.close();
            } else {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }

                bufferedReader.close();
            }

            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static String quickbooksRequest(String realmId, String token, String sql) throws AuthErrorException {
        HttpURLConnection connection = null;
        try {
            String url = new StringBuilder(qbConfig.getString("baseUrl")).append("/").append(realmId).append("/query" +
                    "?query" +
                "=").append(URLEncoder.encode(sql, "UTF-8")).toString();

            URL uri = new URL(url);
            connection = (HttpURLConnection) uri.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Authorization", "Bearer "+token);
            connection.setRequestProperty("Accept", "Application/json");

            return httpResponse(connection);
        }catch(AuthErrorException e){
            throw new AuthErrorException(e.getMessage());
        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.disconnect();
        }
        return null;
    }
}
