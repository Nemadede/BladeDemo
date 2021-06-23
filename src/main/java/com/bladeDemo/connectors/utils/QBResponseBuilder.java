package com.bladeDemo.connectors.utils;

import com.bladeDemo.utils.configs.Configs;
import com.bladeDemo.utils.ResponseBuilder;
import com.typesafe.config.Config;

public class QBResponseBuilder extends ResponseBuilder {

    private static Config conf = new Configs.Builder().withResource("dev.conf").build();
    private String link = conf.getString("app.qb.link");

    public String getLink() {
        return link;
    }
}
