package com.personalwebsite.api.experience;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/experience")
public class ExperienceController {
    private final ExperienceService service;

    public ExperienceController(ExperienceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Long> createExperience(
            @RequestBody ExperienceRequest req) {
        return ResponseEntity.ok(service.createExperience(req));
    }

    @GetMapping
    public ResponseEntity<List<Experience>> readAllExperience() {
        return ResponseEntity.ok(service.readAllExperiences());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateExperience(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("date") String date,
            @RequestParam("description") String description
    ) {
        ExperienceRequest req = new ExperienceRequest(name, date, description);
        service.updateExperience(id, req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable("id") Long id) {
        service.deleteExperience(id);
        return ResponseEntity.ok().build();
    }
}
