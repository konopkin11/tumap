package com.invisibles.admins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class AdminsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminsApplication.class, args);
    }

}
