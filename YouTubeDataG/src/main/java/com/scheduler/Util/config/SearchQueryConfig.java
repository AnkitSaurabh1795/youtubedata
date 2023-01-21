package com.scheduler.Util.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "search-criteria")
public class SearchQueryConfig {
    private String query;
    private String publishedafter;
}
