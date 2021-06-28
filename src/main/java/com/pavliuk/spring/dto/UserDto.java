package com.pavliuk.spring.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    @NotNull
    private Long id;

    @Size(min = 3, message = "{javax.validation.constraints.firstName.size.message}")
    private String firstName;

    @Size(min = 3, message = "{javax.validation.constraints.lastName.size.message}")
    private String lastName;

    @NotEmpty(message = "{javax.validation.constraints.login.NotEmpty.message}")
    private String userLogin;
}
