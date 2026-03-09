package com.green.greenuni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GreenUniApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenUniApplication.class, args);
    }

}
