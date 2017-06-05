package com.example.rest.events.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Application launcher.
 */
@SpringBootApplication(scanBasePackages = "com.example")
@EnableMongoRepositories
public class ApplicationLauncher {

    public static void main(String... args) {
        SpringApplication.run(ApplicationLauncher.class, args);
    }
}
