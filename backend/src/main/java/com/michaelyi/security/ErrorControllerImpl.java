package com.michaelyi.security;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorControllerImpl implements ErrorController {
    @RequestMapping("/error")
    public String error() {
        return "Not Found";
    }
}
