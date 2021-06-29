package com.pavliuk.spring.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_test")
public class UserTest {
    @Id
    private Long id;

    @Column(name = "started_at")
    private String startedAt;

    @Column(name = "finished_at")
    private String finishedAt;

    @ManyToOne
    @JoinColumn(name = "test_id")
    TestEntity test;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    private Double score;
}
