package com.twu.biblioteca.model;

public class RestResponse {

    public enum STATUS {
        SUCCESS,
        FAIL
    }

    private STATUS status;
    private String message;

    public RestResponse(STATUS status, String message) {
        this.status = status;
        this.message = message;
    }

    public STATUS getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
