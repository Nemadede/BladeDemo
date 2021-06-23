package com.bladeDemo.connectors.utils;

import com.blade.validator.Validators;
import com.bladeDemo.connectors.params.QBBusinessParams;

public class QBValidators {

    public static void validateAuth(QBBusinessParams params){
        Validators.notEmpty().test(params.getCode()).throwIfInvalid("code");
        Validators.notEmpty().test(params.getRealmId()).throwIfInvalid("realmId");
        Validators.notEmpty().test(params.getState()).throwIfInvalid("state");
    }

}
