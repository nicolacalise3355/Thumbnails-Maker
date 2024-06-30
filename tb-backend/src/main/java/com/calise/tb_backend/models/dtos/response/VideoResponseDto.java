package com.calise.tb_backend.models.dtos.response;


public class VideoResponseDto {

    private int id;
    private String title;
    private String uri;

    public VideoResponseDto(int id, String title, String uri) {
        this.id = id;
        this.title = title;
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
