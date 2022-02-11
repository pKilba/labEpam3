package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@SpringBootApplication
@ComponentScan(basePackages = "com.epam")
public class LabEpam3Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(LabEpam3Application.class, args);
    }

}
