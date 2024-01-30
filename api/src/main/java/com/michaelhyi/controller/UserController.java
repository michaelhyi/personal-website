package com.michaelhyi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michaelhyi.entity.User;
import com.michaelhyi.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService service;

    @GetMapping("{email}")
    public ResponseEntity<User> readUserByEmail(
            @PathVariable("email") String email
    ) {
        return ResponseEntity.ok(service.readUserByEmail(email));
    }
}