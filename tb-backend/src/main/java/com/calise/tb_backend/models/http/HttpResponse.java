package com.calise.tb_backend.models.http;

public class HttpResponse {

    private int statusCode;

    public HttpResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
