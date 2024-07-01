package com.calise.tb_backend.exceptions.entities;

public class VideoUploadException extends Exception{

    private int internalCodeError;

    public VideoUploadException(String message, int internalCodeError) {
        super(message);
        this.internalCodeError = internalCodeError;
    }

    public int getInternalCodeError() {
        return internalCodeError;
    }

}
