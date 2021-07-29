package com.pavliuk.spring.mapper;

import com.pavliuk.spring.dto.AnswerDto;
import com.pavliuk.spring.dto.QuestionDto;
import com.pavliuk.spring.model.Answer;
import com.pavliuk.spring.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionMapper {
    public static Question createQuestionFromDto(QuestionDto questionDto) {
        Question question = new Question();
        question.setActive(true);
        question.setId(questionDto.getQuestionId());
        question.setTestId(questionDto.getTestId());
        question.setText(questionDto.getQuestionText());
        question.setAnswers(createAnswersFromDto(questionDto));

        return question;
    }

    public static List<Answer> createAnswersFromDto(QuestionDto questionDto) {
        List<Answer> answers = new ArrayList<>();
        for (AnswerDto answerDto : questionDto.getAnswers()) {
            Answer answer = new Answer();
            answer.setText(answerDto.getText());
            answer.setId(answerDto.getId());
            answer.setIsCorrect(answerDto.isCorrect());
            Question tempQuestion = new Question();
            tempQuestion.setId(questionDto.getQuestionId());
            answer.setQuestion(tempQuestion);
            answers.add(answer);
        }

        return answers;
    }
}
