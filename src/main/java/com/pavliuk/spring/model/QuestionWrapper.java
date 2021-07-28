package com.pavliuk.spring.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionWrapper {
    private String text;
    private List<AnswerWrapper> answers;

    public QuestionWrapper(Question question) {
        this.text = question.getText();
        answers = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            answers.add(new AnswerWrapper(answer));
        }
    }

    public boolean isAnsweredCorrectly() {
        for (AnswerWrapper answer : answers) {
            if (answer.isCorrect() ^ answer.isSelected()) {
                return false;
            }
        }

        return true;
    }
}
