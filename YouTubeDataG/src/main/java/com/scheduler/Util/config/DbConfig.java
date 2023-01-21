package com.scheduler.Util.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@ConfigurationProperties(prefix = "mongo-config")
public class DbConfig {
    private String uri;
    private String dbname;
}
