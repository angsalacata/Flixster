package com.example.angsala.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel

public class Movie {
    //values from API
    //private, must use getters to expose information to activity
     String title;
     String overview;
    //this is the path from JSON
     String posterPath;//only give path, which is only part of url
    //backdrop path
     String backdropPath;
     //track voteAverage
     Double voteAverage;

     //default constructor
     public Movie(){

     }


    //intialize from JSON data
    public Movie(JSONObject jobject) throws JSONException {
        //let it throw the exception
        title = jobject.getString("title");
        overview = jobject.getString("overview");
        posterPath = jobject.getString("poster_path");
        backdropPath = jobject.getString("backdrop_path");
        voteAverage = jobject.getDouble("vote_average");



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

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getVoteAverage() { return voteAverage;}
}
