package com.pavliuk.spring.mapper;

import com.pavliuk.spring.dto.QuestionDto;
import com.pavliuk.spring.model.Question;

public class QuestionMapper {
    public static Question createQuestionFromDto(QuestionDto questionDto) {
        Question question = new Question();
        question.setActive(true);
        question.setId(questionDto.getQuestionId());
        question.setTestId(questionDto.getTestId());
        question.setAnswers(questionDto.getAnswers());
        question.setText(questionDto.getQuestionText());

        return question;
    }
}
