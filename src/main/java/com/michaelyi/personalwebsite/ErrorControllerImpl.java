package com.michaelyi.personalwebsite;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotFoundController implements ErrorController {
    @GetMapping("/error")
    public String error() {
        return "404 page not found";
    }
}
