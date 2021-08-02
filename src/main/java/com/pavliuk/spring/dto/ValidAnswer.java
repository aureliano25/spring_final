package com.pavliuk.spring.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AnswersValidator.class)
@Documented
public @interface ValidAnswer {
    String message() default "{javax.validation.constraints.answer.correct}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}