package com.example.picshare.dto;

public class PostDto {

    private String title;
    private String content;
    private String imageUrl;
    private String author;

    public PostDto() {
    }

    public PostDto(String title, String content, String author, String imageUrl) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.imageUrl = imageUrl;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

