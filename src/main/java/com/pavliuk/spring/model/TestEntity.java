package com.pavliuk.spring.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "test")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String difficulty;
    private Long timer;
    private String title;
}
