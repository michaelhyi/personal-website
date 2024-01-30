package com.michaelhyi.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @Setter(AccessLevel.NONE)
    @Column(updatable = false)
    private String id;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    @Column(updatable = false)
    private Date date;

    @Column(unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
}