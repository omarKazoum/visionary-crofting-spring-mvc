package com.visionary.crofting.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Data
@ConfigurationProperties(prefix = "com.visionary-crofting")
public class MvcConfig implements WebMvcConfigurer {
    private String name="";
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    /*    registry.addViewController("/").setViewName("home.html");
        registry.addViewController("/home").setViewName("home.html");*/
    }


}
