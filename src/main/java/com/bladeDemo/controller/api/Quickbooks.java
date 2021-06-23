package com.bladeDemo.controller.api;

import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.bladeDemo.connectors.utils.QBService;
import com.bladeDemo.commons.models.quickbooks.QBBusiness;
import com.bladeDemo.commons.params.DataResponse;
import com.bladeDemo.connectors.params.OauthResponse;
import com.bladeDemo.connectors.params.LinkResponse;
import com.bladeDemo.connectors.params.QBBusinessParams;
import com.bladeDemo.controller.session.utils.AuthService;
import com.bladeDemo.connectors.utils.QuickBooksOAuth;
import com.bladeDemo.connectors.quickbooks.UserTask;
import com.bladeDemo.utils.configs.GlobalConfigs;
import com.bladeDemo.connectors.utils.QBResponseBuilder;
import com.bladeDemo.connectors.utils.QBValidators;
import com.intuit.oauth2.exception.InvalidRequestException;

import java.net.URI;
import java.net.URISyntaxException;

@Path("/quickbooks")
public class Quickbooks extends QBResponseBuilder {

    private final GlobalConfigs configs = GlobalConfigs.getInstance();

    @GetRoute("/uri")
    @JSON
    public void getUri(Request request, Response response){
        try {

            String uri = QuickBooksOAuth.generateOauthUrl();
            response.status(200);
            response.body(toJson(LinkResponse.builder()
                    .link(uri)
                    .build()));

        } catch (InvalidRequestException e) {
            response.status(500);
            response.body(e.getMessage());
        }
    }


    // Front End walava

    @GetRoute
    public void redirect(Response response){
        try {
            URI url = new URI(getLink());
            response.redirect(String.valueOf(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @GetRoute("/oauth")
    public void oauth(Response response, @Param String state,
                      @Param String realmId,
                      @Param String code){
        response.body(toJson(OauthResponse.builder()
                .code(code)
                .realmId(realmId)
                .state(state)
                .build()));
        response.status(200);
    }

    // End of Front end Walava

    @PostRoute("/authenticate")
    @JSON
    public void authenticate(Request request, Response response, @BodyParam QBBusinessParams params){
        DataResponse data = AuthService.verifyToken(request);

        if(!data.isSuccess()){
            response.body(errorMessage(data.getMessage()));
            response.status(401);
            return;
        }

        String email = com.bladeDemo.utils.JSON.parseToMap(toJson(data.getData())).get("email");

        try{
            QBValidators.validateAuth(params);

            int id = QBService.createQBBusiness(params,email);

            configs.getUserTaskExecutor().execute(new UserTask(QBService.getQBBusinessById(id)));

        }
        catch (Exception e){
            response.body(errorMessage(e.getMessage()));
            response.status(400);
            return;
        }


        response.body(toJson(messageResponse(true, "connected", null)));
        response.status(200);
    }

    @GetRoute("/test")
    public void test(){

            QBBusiness qbBusiness = QBService.getQBBusinessById(1);
            configs.getUserTaskExecutor().execute(new UserTask(qbBusiness));
            QBBusiness qbBusiness2 = QBService.getQBBusinessById(2);
            configs.getUserTaskExecutor().execute(new UserTask(qbBusiness2));

    }

    @PostRoute("/webhooks")
    public void hook_callback(Request request, Response response){
        System.out.println(request.body());
    }


}
