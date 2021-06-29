package com.pavliuk.spring.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TestDto {
    private Long id;

    @NotEmpty
    private String title;

    @NotNull
    private Long subject;

    @NotEmpty
    private String difficulty;

    @NotNull
    @Min(1)
    private Long time;
}
