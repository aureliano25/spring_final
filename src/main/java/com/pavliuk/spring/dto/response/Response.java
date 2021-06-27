package com.pavliuk.spring.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Response {
    private Status status;
    private Object message;
    private Object errors;

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

    public enum Status {
        OK, BAD_REQUEST
    }
}
