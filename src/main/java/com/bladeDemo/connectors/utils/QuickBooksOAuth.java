package com.bladeDemo.connectors.utils;

import com.bladeDemo.utils.configs.Configs;
import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuth2Authorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.config.Scope;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.intuit.oauth2.exception.InvalidRequestException;
import com.intuit.oauth2.exception.OAuthException;
import com.typesafe.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class QuickBooksOAuth {

    private static Config conf = new Configs.Builder().withResource("dev.conf").build();
    private static Config qbConfig = conf.getConfig("app.qb");
    private static final String clientSecret = qbConfig.getString("client_secret");
    private static final String clientId = qbConfig.getString("client_id");
    private static final String redirectUrl = qbConfig.getString("redirect_uri");

    private static OAuth2Config oauth2Config = new OAuth2Config.OAuth2ConfigBuilder(clientId,clientSecret)
            .callDiscoveryAPI(Environment.SANDBOX).buildConfig();


    private static OAuth2PlatformClient client = new OAuth2PlatformClient(oauth2Config);

    private static Semaphore semaphore = SemaphoreControls.getInstance().getSemaphore();

    public static String generateOauthUrl() throws InvalidRequestException {
        String crsf = oauth2Config.generateCSRFToken();

        List<Scope> scopes = new ArrayList<>();
        scopes.add(Scope.Accounting);

        return oauth2Config.prepareUrl(scopes, redirectUrl, crsf);
    }

    public static Map<String, String> getBearerToken(String authCode) throws OAuthException {
        Map<String, String> response = new HashMap<>();

        try{
            BearerTokenResponse bearerTokenResponse = client.retrieveBearerTokens(authCode,redirectUrl);
            response.put("token", bearerTokenResponse.getAccessToken());
            response.put("refresh-token", bearerTokenResponse.getRefreshToken());
            return response;

        } catch (OAuthException e){
            System.out.println("The Impossible happened");
            throw new OAuthException("Error at Token Generation + " + e.fillInStackTrace());
        }
    }

    public static Map<String, String> refreshBearerToken(String refresh_token) throws OAuthException {
        Map<String, String> response = new HashMap<>();

        try{
            BearerTokenResponse bearerTokenResponse = client.refreshToken(refresh_token);
            response.put("token", bearerTokenResponse.getAccessToken());
            response.put("refresh-token", bearerTokenResponse.getRefreshToken());

            return response;

        } catch (OAuthException e) {
            semaphore.release();
           throw new OAuthException("Error at Token Refresh + " + e.fillInStackTrace());
        }
    }

    public static String getData(String realmId, String accessToken, String sql) throws AuthErrorException {

        String res =  HttpBuilder.quickbooksRequest(realmId, accessToken, sql);

        return res;
    }

    public static DataService getDataService(String realmId, String accessToken) throws FMSException {

        com.intuit.ipp.util.Config.setProperty(com.intuit.ipp.util.Config.BASE_URL_QBO, qbConfig.getString("baseUrl"));

        OAuth2Authorizer oauth = new OAuth2Authorizer(accessToken);

        Context context = new Context(oauth, ServiceType.QBO, realmId);

        return new DataService(context);
    }


}
