package com.calise.tb_backend.models.http;

public class HttpResponseInvalid extends HttpResponse{

    private String message;

    public HttpResponseInvalid(int statusCode, String message) {
        super(statusCode);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

