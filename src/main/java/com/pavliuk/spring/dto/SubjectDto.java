package com.pavliuk.spring.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SubjectDto {
    @NotNull
    private Long id;

    @NotEmpty
    private String name;
}
