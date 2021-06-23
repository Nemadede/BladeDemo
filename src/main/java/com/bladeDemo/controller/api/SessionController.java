package com.bladeDemo.controller.api;

import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.ui.RestResponse;
import com.bladeDemo.controller.session.utils.Emailer;
import com.bladeDemo.controller.session.utils.SessionService;
import com.bladeDemo.commons.models.User;
import com.bladeDemo.commons.params.DataResponse;
import com.bladeDemo.controller.session.utils.AuthService;
import com.bladeDemo.controller.session.params.*;
import com.bladeDemo.controller.session.utils.SessionResponseBuilder;
import com.bladeDemo.controller.session.utils.SessionValidator;

import java.util.Map;


@Path("/session")
public class SessionController extends SessionResponseBuilder {

    @PostRoute("/sign_up")
    @JSON
    public void save(@BodyParam SignUpParams params, Response response){

        try {
            SessionValidator.validateSignUp(params);

            if (SessionService.getUserByEmail(params.getEmail()) != null){
                response.body(errorMessage("Email already Exist"));
                response.status(400);
                return;
            }
            SessionService.insertUser(params);

            response.body(toJson(Message.builder()
                    .success(true)
                    .message("email sent to "+params.getEmail())));

        }
        catch (Exception e){
            e.printStackTrace();
            response.status(400);
            response.body(errorMessage(e.getMessage()));
        }
    }

    @GetRoute("/verify/:token")
    @JSON
    public void afterMailer(@PathParam String token, Response response){
        System.out.println(token);
        try {
            SessionService.checkUserByToken(token);
            User user = SessionService.setUserActive(token);

            response.body(toJson(toSignUp(null,"SignUp complete", true)));
            response.status(200);
        } catch (Exception e) {
            response.status(400);
            response.body(errorMessage("Error: "+ e.getMessage()));
        }

    }

    @PostRoute("/resend-email-verification-link")
    public void resendEmail(@BodyParam ResendParams params, Response response){
        User user = SessionService.getUserByEmail(params.getEmail());
        if (user == null){
            response.body("invalid user");
            return;
        }
        Emailer.emailVerification(null, user.getEmail(), user.getToken());

        response.body("message Resent");
    }


    @PostRoute("/login")
    @JSON
    public void login(@BodyParam LoginParams params, Response response){

        try{
            // Check if email exist
            if(!SessionService.checkUserByEmail(params.getEmail())){
                response.body(errorMessage("Invalid user Credentials"));
                response.status(400);
                return;
            }
            // Check password
            if (!SessionService.checkPassword(params)){
                response.body(errorMessage("Invalid user Credentials"));
                response.status(400);
                return;
            }

            User user = SessionService.getUserByEmail(params.getEmail());
            if (user.getActive() == null || !user.getActive()){

                Emailer.verifyEmailMailer(user.getEmail());
                response.body(toJson(messageResponse(false, "verify email",null)));
                return;
            }

            Map<String, String> tokens = AuthService.authenticate(user);
            response.body(toJson(loginResponse(user, tokens)));

        } catch (Exception e){
            e.printStackTrace();
            response.body(errorMessage(e.getMessage()));
            response.status(400);
        }

    }

    @GetRoute("/testings")
    @JSON
    public void someRoute(Request request, Response response){
        DataResponse data = AuthService.verifyToken(request);

        if(!data.isSuccess()){
            response.body(errorMessage(data.getMessage()));
            response.status(401);
            return;
        }


        response.status(200);
        String email = com.bladeDemo.utils.JSON.parseToMap(toJson(data.getData())).get("email");
        System.out.println(email);

        response.body(toJson(messageResponse(true, null, data.getData())));

    }


    @PostRoute("/refresh-token")
    @JSON
    public void refresh_token(@BodyParam RefreshTokenParams params, Response response){
        DataResponse res = AuthService.refreshToken(params.getRefreshToken());
        if(!res.isSuccess()) {
            response.status(401);
            response.body(toJson(res));
            return;
        }

        response.status(200);
        response.body(toJson(res));
    }



    @GetRoute("/user/:id")
    @JSON
    public void getUser(@PathParam String id, Request request, Response response){
        DataResponse data = AuthService.verifyToken(request);

        if(!data.isSuccess()){
            response.body(errorMessage(data.getMessage()));
            response.status(401);
            return;
        }

        Map<String, String> map = com.bladeDemo.utils.JSON.parseToMap(toJson(data.getData()));

        User user = SessionService.getUserByEmail(map.get("email"));

        response.status(200);
        response.body(toJson(toSignUp(user,null, true)));

    }

    @DeleteRoute("/:id")
    public RestResponse deleteUser(@PathParam Integer id){
        return RestResponse.ok(SessionService.deleteUser(id));

    }

    // Forgot Password

    // Todo: Send email to sendPassword
    @PostRoute("/forgot-password")
    @JSON
    public void passwordResetReq(@BodyParam ResendParams params, Response response){
        try {
            SessionValidator.validateResends(params);

            User user = SessionService.getUserByEmail(params.getEmail());
            if(user == null){
                response.status(400);
                response.body(errorMessage("invalid user"));
                return;
            }

            if(!user.getActive()){
                // resend email verification
                Emailer.verifyEmailMailer(params.getEmail());
                response.status(400);
                response.body(toJson(messageResponse(false, "verify email",null)));
                return;
            }

            // send password reset....
            SessionService.forgotPassword(user.getEmail());
            response.status(200);
            response.body(toJson(messageResponse(true, "email sent", null)));

        }
        catch (Exception e){
            response.status(400);
            response.body(errorMessage("Email required"));
        }
    }
    // todo:

    @GetRoute("/verify-password/:token")
    @JSON
    public void verify(@PathParam String token, Response response){
        response.body(toJson(messageResponse(true, null, null)));
    }

    @PostRoute("/reset-password")
    @JSON
    public void resetPassword(@BodyParam PasswordResetParams params, Response response){
        try{
            SessionValidator.validatePasswordReset(params);

            User user = SessionService.UpdatePassword(params.getSecret(), params.getNew_password());
            if(user == null){
                response.status(400);
                response.body(errorMessage("invalid credentials"));
                return;
            }

            response.status(200);
            response.body(toJson(messageResponse(true, "Password updated",null)));


        }
        catch (Exception e){
            response.status(400);
            response.body(errorMessage(e.getMessage()));
        }
    }
}
