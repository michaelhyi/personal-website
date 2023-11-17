package com.personalwebsite.api.project;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Long createProject(ProjectRequest req) {
        Project project = new Project(
                req.name(),
                req.date(),
                req.description(),
                req.tech(),
                req.image(),
                req.href());
        repository.saveAndFlush(project);

        return project.getId();
    }

    public List<Project> readAllProjects() {
        List<Project> projects = repository.findAll();
        Collections.reverse(projects);
        return projects;
    }

    @Transactional
    public void updateProject(Long id, ProjectRequest req) {
        Project project = repository
                .findById(id)
                .orElseThrow(RuntimeException::new);

        project.setName(req.name());
        project.setDate(req.date());
        project.setDescription(req.description());
        project.setTech(req.tech());
        project.setImage(req.image());
        project.setHref(req.href());
    }

    public void deleteProject(Long id) {
        repository.deleteById(id);
    }
}
