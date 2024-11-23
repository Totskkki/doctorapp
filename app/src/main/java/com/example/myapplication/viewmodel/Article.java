package com.example.myapplication.viewmodel;

// Article class remains unchanged
public class Article {

    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public Article(String publishedAt, String urlToImage, String url, String description, String title) {
        this.publishedAt = publishedAt;
        this.urlToImage = urlToImage;
        this.url = url;
        this.description = description;
        this.title = title;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}

