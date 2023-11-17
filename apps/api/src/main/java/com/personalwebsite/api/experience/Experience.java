package com.personalwebsite.api.experience;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Experience {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String date;
    private String description;

    public Experience(String name, String date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public Experience() {
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
}
