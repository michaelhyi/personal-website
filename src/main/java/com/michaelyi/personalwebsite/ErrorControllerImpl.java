package com.michaelyi.personalwebsite;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorControllerImpl implements ErrorController {
    @GetMapping("/error")
    public ResponseEntity<String> error() {
        return new ResponseEntity<>("404 page not found", HttpStatus.NOT_FOUND);
    }
}
