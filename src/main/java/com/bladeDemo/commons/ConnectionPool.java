package com.bladeDemo.commons;

import com.typesafe.config.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {

    private ConnectionPool(){}

    public static HikariDataSource getDataSourceFromConfig(Config conf) {
        String url = conf.getString("jdbcUrl");

        HikariConfig jdbcConfig = new HikariConfig();
        jdbcConfig.setPoolName(conf.getString("poolName"));
        jdbcConfig.setMaximumPoolSize(conf.getInt("maximumPoolSize"));
        jdbcConfig.setMinimumIdle(conf.getInt("minimumIdle"));
        jdbcConfig.setJdbcUrl(url);
        //jdbcConfig.setAutoCommit(false);
        jdbcConfig.setUsername(conf.getString("username"));
        jdbcConfig.setPassword(conf.getString("password"));
        jdbcConfig.addDataSourceProperty("cachePrepStmts", conf.getBoolean("cachePrepStmts"));
        jdbcConfig.addDataSourceProperty("prepStmtCacheSize", conf.getInt("prepStmtCacheSize"));
        jdbcConfig.addDataSourceProperty("prepStmtCacheSqlLimit", conf.getInt("prepStmtCacheSqlLimit"));
        jdbcConfig.addDataSourceProperty("useServerPrepStmts", conf.getBoolean("useServerPrepStmts"));
        // Add HealthCheck
        //jdbcConfig.setHealthCheckRegistry(healthCheckRegistry);
        // Add Metrics
        //jdbcConfig.setMetricRegistry(metricRegistry);
        return new HikariDataSource(jdbcConfig);
    }

}
