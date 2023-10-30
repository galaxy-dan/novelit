package com.galaxy.novelit.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,PATCH,OPTIONS";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
            .allowedHeaders("*");
    }
}
