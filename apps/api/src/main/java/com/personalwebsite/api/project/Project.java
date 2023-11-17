package com.personalwebsite.api.project;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String date;
    private String description;
    private String tech;

    @Column(nullable = true)
    private String image;
    private String href;

    public Project(String name,
                   String date,
                   String description,
                   String tech,
                   String image,
                   String href) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.tech = tech;
        this.image = image;
        this.href = href;
    }

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
