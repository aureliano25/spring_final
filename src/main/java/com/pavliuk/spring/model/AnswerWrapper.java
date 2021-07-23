package com.pavliuk.spring.model;

import lombok.Data;

@Data
public class AnswerWrapper {
    private boolean isSelected;
    private boolean isCorrect;
    private String text;

    public AnswerWrapper(Answer answer){
        this.isCorrect = answer.getIsCorrect();
        this.text = answer.getText();
    }
}
