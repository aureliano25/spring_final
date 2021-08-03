package com.pavliuk.spring.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class QuestionDto {
    private Long questionId;
    private String questionText;
    private Long testId;

    @Size(min = 2, message = "{javax.validation.constraints.answer.size}")
    @ValidAnswer
    private List<AnswerDto> answers;

    private List<Long> answersToDelete;
}
