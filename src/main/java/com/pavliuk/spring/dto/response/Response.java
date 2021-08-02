package com.pavliuk.spring.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class Response {
    private Status status;
    private String message;
    private List<String> errors;

    public static Response ok() {
        Response response = new Response();
        response.setStatus(Status.OK);

        return response;
    }

    public static Response badRequest() {
        Response response = new Response();
        response.setStatus(Status.BAD_REQUEST);

        return response;
    }

    public Response setErrors(Errors errors) {
        this.errors = errors.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return this;
    }

    public enum Status {
        OK, BAD_REQUEST
    }
}
