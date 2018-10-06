package com.example.noura.movieapp;

public class Review {
    String Author, content;
    int ReviewID;

    public Review(int ReviewID, String author, String content) {
        Author = author;
        this.content = content;
        this.ReviewID = ReviewID;
    }

    public int getReviewID() {
        return ReviewID;
    }

    public void setReviewID(int reviewID) {
        ReviewID = reviewID;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
