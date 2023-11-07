package com.meta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/areaImage/**")
        .addResourceLocations("file:////home/ubuntu/files/area_image/");
        
		registry.addResourceHandler("/Qr_Code/**")
		.addResourceLocations("file:////home/ubuntu/files/Qr_Code/");
    }
}