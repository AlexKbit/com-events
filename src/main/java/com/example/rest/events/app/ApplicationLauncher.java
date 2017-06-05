package com.example.rest.events.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application launcher.
 */
@SpringBootApplication(scanBasePackages = "com.example")
public class ApplicationLauncher {

    public static void main(String... args) {
        SpringApplication.run(ApplicationLauncher.class, args);
    }
}
