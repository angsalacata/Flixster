package com.example.angsala.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//this is the configuration object
public class Config {
    //the base url for loading images
    String imageBaseUrl;
    //the size of poster to fetch image, part of the url
    String posterSize;

    //new field for orientation manipulation
    String backdropSize;

    //constructor for config

    public Config(JSONObject object) throws JSONException {
        //this line is needed to correctly parse the JSON object! otherwise, didn't read object right
        JSONObject images = object.getJSONObject("images");
        imageBaseUrl = images.getString("secure_base_url");
        //get poster size, remember from array of objects
        JSONArray posterSizeArray = images.getJSONArray("poster_sizes");
        //know that the poster size wanted is index number 3
        posterSize = posterSizeArray.optString(3, "w342");
        //parse backdrop sizes, should get backdrop size w780
        JSONArray backdropSizeArray = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeArray.optString(1,"w780");
    }

    //helper method for creating URLS
    public String getimageURL(String posterSize, String pathUrl){
        //not referencing posterSize directly from class, to be more generic and flexible
        return String.format("%s%s%s", imageBaseUrl, posterSize, pathUrl);
    }

    //getters for returning the image base URL and posterSize
    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
