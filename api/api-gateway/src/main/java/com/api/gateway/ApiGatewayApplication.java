package com.api.gateway;

import com.api.gateway.auth.dto.RegReq;
import com.api.gateway.user.UserRole;
import com.api.gateway.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserService service) {
        return args -> {
            var user = new RegReq("michaelyi@gatech.edu", "password", true, List.of(UserRole.ROLE_ADMIN));
            service.createUser(user);
        };
    }
}