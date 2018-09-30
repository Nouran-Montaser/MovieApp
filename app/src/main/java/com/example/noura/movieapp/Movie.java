package com.example.noura.movieapp;

public class Movie {

    public static final String baseImageUrl = "http://image.tmdb.org/t/p/w185/";

    public int ID;
    public String Name,posterPath,Title , backdrop_path,OverView,vote_average,Date;

    public Movie(int ID,String Title, String posterPath) {
        this.ID = ID ;
        this.Title = Title;
        this.posterPath = posterPath;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverView() {
        return OverView;
    }

    public void setOverView(String overView) {
        OverView = overView;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Movie(String backdrop_path, String posterPath, String title, String overView, String vote_average, String date) {
        this.posterPath = posterPath;
        Title = title;
        this.backdrop_path = backdrop_path;
        OverView = overView;
        this.vote_average = vote_average;
        Date = date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}


