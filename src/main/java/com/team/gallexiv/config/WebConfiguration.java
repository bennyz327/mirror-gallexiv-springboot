package com.team.gallexiv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 跨域設定
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Value("${gallexiv.allowedApi}")
    private String allowApps;
//    @Value("${allowed.headers}")
//    private String allowedHeaders;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration reg = registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE");
        reg.allowedOrigins(allowApps.split(","));
    }

}
