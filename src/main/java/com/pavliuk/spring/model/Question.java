package com.pavliuk.spring.model;

import lombok.Data;
import com.pavliuk.spring.util.AnswerConverter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_id")
    private Long testId;

    private String text;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;
}
