package com.bladeDemo.controller.session.utils;

import com.blade.validator.Validators;
import com.bladeDemo.controller.session.params.PasswordResetParams;
import com.bladeDemo.controller.session.params.ResendParams;
import com.bladeDemo.controller.session.params.SignUpParams;

public class SessionValidator {

    public static void validateSignUp(SignUpParams params){
        Validators.notEmpty().test(params.getEmail()).throwIfInvalid("Email");

        Validators.notEmpty().test(params.getPassword()).throwIfInvalid("Password");
        Validators.moreThan(8).test(params.getPassword()).throwIfInvalid("Password");
        Validators.lessThan(50).test(params.getPassword()).throwIfInvalid("Password");

        Validators.notEmpty().test(params.getFull_name()).throwIfInvalid("Full name");
        Validators.moreThan(2).test(params.getFull_name()).throwIfInvalid("Full name");

    }

    public static void validateResends(ResendParams params){
        Validators.notEmpty().test(params.getEmail()).throwIfInvalid("Email");
    }

    public static void validatePasswordReset(PasswordResetParams params){
        Validators.notEmpty().test(params.getSecret()).throwIfInvalid("Secret Key");


        Validators.notEmpty().test(params.getNew_password()).throwIfInvalid("New password");
        Validators.moreThan(8).test(params.getNew_password()).throwIfInvalid("New password");
        Validators.lessThan(50).test(params.getNew_password()).throwIfInvalid("New password");

    }
}
