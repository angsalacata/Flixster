package com.example.angsala.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.angsala.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    //constants, the keys needed to use the api
    //Here's the base URL
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    //Here's the parameter name for API key
    public final static String API_KEY_PARAM = "api_key";

    //tag for logging calls from this MovieListActivity
    public final static String TAG = "MovieListActivity";

    //instance new imported fields, not yet initialized
    AsyncHttpClient client;

    //the base url for loading images
    String imageBaseUrl;
    //the size of poster to fetch image, part of the url
    String posterSize;

    //the list of currently playing movies, stored in an array
    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        //initialize the client
        client = new AsyncHttpClient();

        //initialize list of movies
        movies = new ArrayList<Movie>();

        //can now get the configuration, add after client is made
        getConfiguration();


    }

    //get the configuration from the API. Remember configuration is the endpoint with
    private void getConfiguration(){
        //create the url to be accessed
        //string concatenation, use the Base Url and the configuration endpoint
        String url = API_BASE_URL + "/configuration";
        //set up the request parameters- seen in the documentation for async
        RequestParams params = new RequestParams();
        //will look at using the api_key in the secret file
        params.put(API_KEY_PARAM, getString(R.string.api_key));

        //execute the GET request, expecting a JSON object. Take care of the secure base url/poster sizes
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //getting values out of the JSON object, surround with a try/catch
                try {
                    //this line is needed to correctly parse the JSON object! otherwise, didn't read object right
                    JSONObject images = response.getJSONObject("images");
                    imageBaseUrl = images.getString("secure_base_url");
                    //get poster size, remember from array of objects
                    JSONArray posterSizeArray = images.getJSONArray("poster_sizes");
                    //know that the poster size wanted is index number 3
                    posterSize = posterSizeArray.optString(3, "w342");
                    Log.i(TAG, String.format("Loaded configuration with Base URL %s and Poster Size %s", imageBaseUrl, posterSize));
                    //can now get the now_playing movie list
                    getNowPlaying();
                } catch (JSONException e) {
                    //alert user of error when parsing
                    logError("Failed parse in configuration api", e, true);
                }
            }
            //use most simple onFailure method
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
              logError("Get Request Failed", throwable, true);
            }
        });
    }

    //get the list of currently playing movies, similar to how getConfiguration was made
    private void getNowPlaying(){
        //create the url, use API as parameters, etc
        //find this endpoint on the database
        String url = API_BASE_URL + "/movie/now_playing";
        //set up the request parameters- seen in the documentation for async
        RequestParams params = new RequestParams();
        //will look at using the api_key in the secret file
        params.put(API_KEY_PARAM, getString(R.string.api_key));

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray results = response.getJSONArray("results");
                    //parse as a JSON array. You can see it is a JSON array called results
                    //iterate through to add
                    for (int i=0; i < results.length(); ++i){
                        Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);
                    }
                    //this will put the results.length() into the %s
                    Log.i(TAG, String.format("Loaded %s movies", results.length()));
                }

                catch (JSONException e){
                    logError("Failed Parse in now_playing api", e, true); }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
               logError("Request from now_playing failed", throwable, true);
            }
        });
    }


    //handle errors, log and alert the user
    private void logError(String message, Throwable e, boolean alertUser){
        //always log error for developer
        Log.e(TAG, message, e);
        //alert the user using a toast
        if (alertUser){
            Toast. makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
