package com.personalwebsite.api.project;

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
@RequestMapping("api/v1/project")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Long> createProject(@RequestBody ProjectRequest req) {
        return ResponseEntity.ok(service.createProject(req));
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> readProject(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.readProject(id));
    }

    @GetMapping
    public ResponseEntity<List<Project>> readAllProjects() {
        return ResponseEntity.ok(service.readAllProjects());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateProject(
            @PathVariable("id")
            Long id,
            @RequestParam("name")
            String name,
            @RequestParam("date")
            String date,
            @RequestParam("description")
            String description,
            @RequestParam("tech")
            String tech,
            @RequestParam("image")
            String image,
            @RequestParam("href")
            String href) {
        ProjectRequest req =
                new ProjectRequest(name, date, description, tech, image, href);
        service.updateProject(id, req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") Long id) {
        service.deleteProject(id);
        return ResponseEntity.ok().build();
    }
}
