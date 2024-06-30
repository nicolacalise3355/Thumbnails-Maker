package com.calise.tb_backend.exceptions.entities;

public class VideoException extends Exception{

    private int internalCodeError;

    public VideoException(String message, int internalCodeError) {
        super(message);
        this.internalCodeError = internalCodeError;
    }
}
