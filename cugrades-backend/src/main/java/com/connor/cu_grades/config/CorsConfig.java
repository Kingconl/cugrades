package com.connor.cu_grades.config;


import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                "https://your-frontend.pages.dev",
                                "https://cugrades.ca",
                                "https://www.cugrades.ca"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedOrigins(
                                "http://localhost:5173",
                                "https://cugrades.ca",
                                "https://www.cugrades.ca"
                        );
            }
        };
    }
}
