package com.example.noura.movieapp.Data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.noura.movieapp.Trailer;

import java.util.ArrayList;

@Entity(tableName = "Movies")
public class Movies implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String posterPath;

    private String Title ;

    private String backdrop_path;

    private String OverView;

    private String vote_average;

    private String Date;

    public static final String baseImageUrl = "http://image.tmdb.org/t/p/w185/";


    @Ignore
    public Movies(String name,String posterPath, String Title, String backdrop_path, String OverView, String vote_average, String Date) {
        this.name = name;
        this.posterPath = posterPath;
        this.Title = Title;
        this.backdrop_path = backdrop_path;
        this.OverView = OverView;
        this.vote_average = vote_average;
        this.Date = Date;
    }

    public Movies(int id, String name, String posterPath, String Title,String  backdrop_path, String OverView, String vote_average, String Date/*, ArrayList<Trailer> trailers*/) {
        this.id = id;
        this.name = name;
        this.posterPath = posterPath;
        this.Title = Title;
        this.backdrop_path = backdrop_path;
        this.OverView = OverView;
        this.vote_average = vote_average;
        this.Date = Date;
//        this.trailers = trailers;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String  getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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

    public static String getBaseImageUrl() {
        return baseImageUrl;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", Title='" + Title + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", OverView='" + OverView + '\'' +
                ", vote_average='" + vote_average + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.posterPath);
        dest.writeString(this.Title);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.OverView);
        dest.writeString(this.vote_average);
        dest.writeString(this.Date);
    }

    protected Movies(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.posterPath = in.readString();
        this.Title = in.readString();
        this.backdrop_path = in.readString();
        this.OverView = in.readString();
        this.vote_average = in.readString();
        this.Date = in.readString();
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}