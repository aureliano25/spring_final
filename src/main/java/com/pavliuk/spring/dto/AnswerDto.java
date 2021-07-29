package com.pavliuk.spring.dto;

import lombok.Data;

@Data
public class AnswerDto {
    private Long id;
    private String text;
    private boolean isCorrect;
}
