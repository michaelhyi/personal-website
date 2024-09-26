package com.michaelyi.personalwebsite.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/")
    public ResponseEntity<HealthResponse> getHealth() {
        HealthResponse res = new HealthResponse();

        try {
            Health health = healthService.getHealth();
            res.setHealth(health);
            res.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            res.setError("Internal server error");
            res.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(res, res.getHttpStatus());
    }
}
