package com.Sportagram.sportagram.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true)
                .maxAge(3600);
    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 이미지 리소스를 제공하기 위한 핸들러
//        registry.addResourceHandler("/teamIcon/**")  // '/teamIcon/'으로 시작하는 요청을 처리
//                .addResourceLocations("classpath:/static/teamIcon/");  // 실제 리소스 위치 (resources/static/teamIcon)
//    }
}
