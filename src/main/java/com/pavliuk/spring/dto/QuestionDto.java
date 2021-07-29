package com.pavliuk.spring.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    private Long questionId;
    private String questionText;
    private Long testId;
    private List<AnswerDto> answers;
}
