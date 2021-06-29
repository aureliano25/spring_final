package com.pavliuk.spring.model;

import lombok.Data;
import com.pavliuk.spring.util.AnswerConverter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Question {
    @Id
    private Long id;

    @Column(name = "test_id")
    private Long testId;

    private String text;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "answers", columnDefinition = "json")
    @Convert(converter = AnswerConverter.class)
    private List<Answer> answers;
}
