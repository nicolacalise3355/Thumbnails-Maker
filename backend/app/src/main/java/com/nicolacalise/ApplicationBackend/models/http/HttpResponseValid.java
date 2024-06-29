package com.nicolacalise.ApplicationBackend.models.http;

public class HttpResponseValid extends HttpResponse {

    private Object value;

    public HttpResponseValid(int statusCode, Object value) {
        super(statusCode);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
