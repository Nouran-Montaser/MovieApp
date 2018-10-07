package com.example.noura.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

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

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(Name);
//        dest.writeString(posterPath);
//        dest.writeString(Title);
//        dest.writeString(backdrop_path);
//        dest.writeString(OverView);
//        dest.writeString(vote_average);
//        dest.writeString(Date);
//        dest.writeInt(ID);
//    }
//    public Movie (Parcel parcel)
//    {
//        Name = parcel.readString();
//        posterPath = parcel.readString();
//        Title = parcel.readString();
//        backdrop_path = parcel.readString();
//        OverView = parcel.readString();
//        vote_average = parcel.readString();
//        Date = parcel.readString();
//        ID = parcel.readInt();
//    }
//

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.Name);
        dest.writeString(this.posterPath);
        dest.writeString(this.Title);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.OverView);
        dest.writeString(this.vote_average);
        dest.writeString(this.Date);
    }

    protected Movie(Parcel in) {
        this.ID = in.readInt();
        this.Name = in.readString();
        this.posterPath = in.readString();
        this.Title = in.readString();
        this.backdrop_path = in.readString();
        this.OverView = in.readString();
        this.vote_average = in.readString();
        this.Date = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}


