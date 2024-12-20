package com.michaelyi.personalwebsite;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/about")
                .setViewName("forward:/about.html");
        registry.addViewController("/portfolio")
                .setViewName("forward:/portfolio.html");
        registry.addViewController("/lauren")
                .setViewName("forward:/lauren.html");
    }
}
