package com.pavliuk.spring.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserTestWrapper {
    private List<QuestionWrapper> questions;
    private int currentQuestionIndex;
    private long timeLimit;
    private long id;

    public UserTestWrapper(UserTest userTest) {
        timeLimit = userTest.getTest().getTimer() * 60;
        id = userTest.getId();
        questions = new ArrayList<>();
        for (Question question : userTest.getTest().getQuestions()) {
            questions.add(new QuestionWrapper(question));
        }
    }

    public QuestionWrapper nextQuestion() {
        return questions.get(++currentQuestionIndex);
    }

    public QuestionWrapper currentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public boolean hasPrevious() {
        return currentQuestionIndex > 0;
    }

    public boolean hasNext() {
        return currentQuestionIndex < questions.size() - 1;
    }

    public QuestionWrapper previousQuestion() {
        return questions.get(--currentQuestionIndex);
    }

    public void updateSelectedAnswers(List<Integer> selectedAnswers) {
        List<AnswerWrapper> answers = currentQuestion().getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            if (selectedAnswers.contains(i)) {
                answers.get(i).setSelected(true);
                continue;
            }

            answers.get(i).setSelected(false);
        }
    }

    public void update(List<Integer> selectedAnswers, Integer timeLeft) {
        setTimeLimit(timeLeft);
        if (selectedAnswers != null) {
            updateSelectedAnswers(selectedAnswers);
        }
    }
}
