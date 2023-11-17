package com.personalwebsite.api.experience;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ExperienceService {
    private final ExperienceRepository repository;

    public ExperienceService(ExperienceRepository repository) {
        this.repository = repository;
    }

    public Long createExperience(ExperienceRequest req) {
        Experience experience = new Experience(
                req.name(),
                req.date(),
                req.description()
        );

        repository.saveAndFlush(experience);

        return experience.getId();
    }

    public List<Experience> readAllExperiences() {
        List<Experience> experiences = repository.findAll();
        Collections.reverse(experiences);
        return experiences;
    }

    @Transactional
    public void updateExperience(Long id, ExperienceRequest req) {
        Experience experience = repository
                .findById(id)
                .orElseThrow(RuntimeException::new);

        experience.setName(req.name());
        experience.setDate(req.date());
        experience.setDescription(req.description());
    }

    public void deleteExperience(Long id) {
        repository.deleteById(id);
    }
}
