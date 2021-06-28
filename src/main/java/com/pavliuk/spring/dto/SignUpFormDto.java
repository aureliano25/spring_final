package com.pavliuk.spring.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignUpFormDto {
    @NotNull(message = "{javax.validation.constraints.firstName.NotNull.message}")
    @Size(min = 3, message = "{javax.validation.constraints.firstName.size.message}")
    private String firstName;

    @NotNull(message = "{javax.validation.constraints.lastName.NotNull.message}")
    @Size(min = 3, message = "{javax.validation.constraints.lastName.size.message}")
    private String lastName;

    @ValidPassword
    private String password;

    @NotEmpty(message = "{javax.validation.constraints.login.NotEmpty.message}")
    private String login;
}
