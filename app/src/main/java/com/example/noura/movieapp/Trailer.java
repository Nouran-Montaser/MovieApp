package com.example.noura.movieapp;

public class Trailer {

    public static final String TRAILER_BASE_URL = "http://www.youtube.com/watch?v=";

    private String site;

    private String id;

    private String name;

    private String type;

    private String key;

    public Trailer(String site, String id, String name, String type, String key) {
        this.site = site;
        this.id = id;
        this.name = name;
        this.type = type;
        this.key = key;
    }

    public String getSite ()
    {
        return site;
    }

    public void setSite (String site)
    {
        this.site = site;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }

}

