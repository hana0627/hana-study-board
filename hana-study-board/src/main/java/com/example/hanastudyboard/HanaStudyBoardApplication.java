package com.example.hanastudyboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class HanaStudyBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanaStudyBoardApplication.class, args);
    }

}
