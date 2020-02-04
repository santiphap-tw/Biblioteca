package com.twu.biblioteca.model;

public class RestResponse {

    public enum STATUS {
        SUCCESS,
        FAIL
    }

    private STATUS status;
    private Object response;

    public RestResponse(STATUS status, Object response) {
        this.status = status;
        this.response = response;
    }

    public STATUS getStatus() {
        return status;
    }

    public Object getResponse() {
        return response;
    }
}
