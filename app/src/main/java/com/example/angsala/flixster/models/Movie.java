package com.example.angsala.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    //values from API
    //private, must use getters to expose information to activity
    private String title;
    private String overview;
    //this is the path from JSON
    private String posterPath;//only give path, which is only part of url

    //intialize from JSON data
    public Movie(JSONObject jobject) throws JSONException {
        //let it throuw the exception
        title = jobject.getString("title");
        overview = jobject.getString("overview");
        posterPath = jobject.getString("poster_path");



    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
