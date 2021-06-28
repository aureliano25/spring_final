package com.pavliuk.spring.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {
    String message() default "{javax.validation.constraints.password.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
