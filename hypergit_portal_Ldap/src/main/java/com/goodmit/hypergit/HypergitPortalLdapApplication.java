package com.goodmit.hypergit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class HypergitPortalLdapApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HypergitPortalLdapApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

}
