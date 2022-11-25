package com.uiscloud.hypergit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class HypergitRestApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HypergitRestApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

}
