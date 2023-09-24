package com.api.gw;

import com.api.gw.auth.RegReq;
import com.api.gw.user.Role;
import com.api.gw.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ApiGwApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGwApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            UserService service
    ) {
        return args -> {
            var user = new RegReq("michaelyi@gatech.edu", "password", true, List.of(Role.ROLE_USER));
            service.createUser(user);
        };
    }
}