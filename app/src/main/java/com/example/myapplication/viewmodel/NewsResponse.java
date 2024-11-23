package com.example.myapplication.viewmodel;

import java.util.List;
public class NewsResponse {

    private boolean success;
    private List<Article> data;  // Data field instead of articles

    // Getter for success
    public boolean isSuccess() {
        return success;
    }

    // Setter for success
    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Getter for data (articles)
    public List<Article> getData() {
        return data;
    }

    // Setter for data (articles)
    public void setData(List<Article> data) {
        this.data = data;
    }
}



