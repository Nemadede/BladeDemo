package com.bladeDemo.connectors.params;

import lombok.Builder;

@Builder
public class OauthResponse {
    String code;
    String realmId;
    String state;
}
