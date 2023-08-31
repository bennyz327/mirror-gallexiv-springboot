package com.team.gallexiv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

// TODO 因為靠 Security 框架設定 測試拔掉這個類之後會不會有問題
// 跨域設定
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    //從application.properties取得設定值
    @Value("${gallexiv.allowedOrigins}")
    private String allowApps;
    @Value("${gallexiv.allowedMethods}")
    private String allowMethods;

    //TODO 非簡單請求可能會用到的設定
//    @Value("${gallexiv.allowedHeaders}")
//    private String allowedHeaders;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration reg = registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE");
        reg.allowedOrigins(allowApps.split(","));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //把字串轉List設定進去
        corsConfiguration.setAllowedOrigins(Arrays.asList(allowApps.split(",")));
        //把方法字串轉List設定進去
        corsConfiguration.setAllowedMethods(Arrays.asList(allowMethods.split(",")));
        //把設定給UrlBasedCorsConfigurationSource並回傳
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
