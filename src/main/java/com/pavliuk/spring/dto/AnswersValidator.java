package com.pavliuk.spring.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class AnswersValidator implements ConstraintValidator<ValidAnswer, List<AnswerDto>> {

    @Override
    public void initialize(ValidAnswer constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<AnswerDto> value, ConstraintValidatorContext context) {
        return value.stream()
                .anyMatch(AnswerDto::isCorrect);
    }

}
