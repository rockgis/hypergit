package com.goodmit.hypergit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class HypergitIdpApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HypergitIdpApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

}
