package com.bladeDemo.utils.configs;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Configs {

    private Configs(){}

    private static final Config system = ConfigFactory.systemProperties();
    private static final Config properties = new Builder().envAwareApp().build();

    public static Config system() {
        return system;
    }

    public static Config properties() {
        return properties;
    }

    public static <T> T getOrDefault(Config config, String path, BiFunction<Config, String, T> extractor, T defaultValue) {
        if (config.hasPath(path)) {
            return extractor.apply(config, path);
        }
        return defaultValue;
    }

    public static <T> T getOrDefault(Config config, String path, BiFunction<Config, String, T> extractor, Supplier<T> defaultSupplier) {
        if (config.hasPath(path)) {
            return extractor.apply(config, path);
        }
        return defaultSupplier.get();
    }

    public static class Builder{
        private Config conf;

        public Builder() {
            System.out.println("Loading configs first row is highest priority, second row is fallback and so on");
        }

        private Config returnOrFallback(Config config) {
            if (this.conf == null) {
                return config;
            }
            return this.conf.withFallback(config);
        }
        public Builder withResource(String resource) {
            this.conf = returnOrFallback(ConfigFactory.parseResources(resource));
            System.out.println("Loaded config file from resource ({}) " + resource);
            return this;
        }

        public Builder envAwareApp() {
            String env = system.hasPath("env") ? system.getString("env") : "local";
            String envFile = "dev." + env + ".conf";
            return withResource(envFile).withResource("dev1.conf");
        }

        public Config build(){

            this.conf.resolve();

            return this.conf;
        }
    }

    public static void main(String[] args) {
        Configs.properties();
    }
}
