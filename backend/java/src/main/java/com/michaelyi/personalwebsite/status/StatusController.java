package com.michaelyi.personalwebsite.status;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/status")
public class StatusController {
    private final StatusService healthService;

    public StatusController(StatusService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public ResponseEntity<StatusResponse> getStatus() {
        StatusResponse res = new StatusResponse();

        try {
            Status health = healthService.getStatus();
            res.setStatus(health);
            res.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            res.setError("Internal server error");
            res.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(res, res.getHttpStatus());
    }
}
