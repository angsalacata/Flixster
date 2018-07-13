package com.example.angsala.flixster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angsala.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.example.angsala.flixster.MovieAdapter.placeholder;

public class MovieDetailsActivity extends AppCompatActivity {
    //retrieve, unwrap and assign this field in the onCreate method
    Movie movie;
    //Track Textview fields for txtvTitle, txtvOverview textview for new activity for movie details activity
    TextView txtvTitle;
    TextView txtvOverview;
    //Track RatingBar object rbVotingAverage
    RatingBar rbVotingAverage;
    //Backdrop image
    ImageView imvBackdropImage;
int id;
    AsyncHttpClient client;

    String youTubeid;
    public final static String place = "hi";
    //constants, the keys needed to use the api
    //Here's the base URL
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    //Here's the parameter name for API key
    public final static String API_KEY_PARAM = "api_key";

    //tag for logging calls from this MovieListActivity
    public final static String TAG = "MovieListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
//The java.lang.Class.getSimpleName() returns the simple name of the underlying class as given in the source code. Returns an empty string if the underlying class is anonymous.
        client = new AsyncHttpClient();
     txtvTitle = (TextView) findViewById(R.id.txtvTitle);
     txtvOverview = (TextView) findViewById(R.id.txtvOverview);
     rbVotingAverage = (RatingBar) findViewById(R.id.rbVotingAverage);
     imvBackdropImage = (ImageView) findViewById(R.id.imvBackdropImage);


//unwrap the movie passed in via intent, using its simple name as the key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        //Log it
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        //set the title and overview
        txtvTitle.setText(movie.getTitle());
        txtvOverview.setText(movie.getOverview());

       // imvBackdropImage.getImage
        GlideApp.with(this)
                .load(this.getIntent().getStringExtra(placeholder))// this will bring up the backdrop url
                .transform(new RoundedCornersTransformation(15,5))
                //remember this is how we access resources in JAVA, via R file
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .error(R.drawable.flicks_backdrop_placeholder)
                .into(imvBackdropImage);


        //set the ratings bar with scale of 0-10 divide by two. Have to recast it because in JSON parsing cannot get the voteAverage as a Float, only a double
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVotingAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage/2.0f : voteAverage);


    getVideo();

    }


public void getVideo(){
    //create the url, use API as parameters, etc
    //find this endpoint on the database
    //int id = movie.getVidId();
    String url = API_BASE_URL + String.format("/movie/%s/videos", movie.getVidId());
    //set up the request parameters- seen in the documentation for async
    RequestParams params = new RequestParams();
    //will look at using the api_key in the secret file
    params.put(API_KEY_PARAM, getString(R.string.api_key));

    client.get(url, params, new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            try{
                JSONArray a = response.getJSONArray("results");
                JSONObject list = a.getJSONObject(0);
                youTubeid = list.getString("key");
                Log.i(TAG, String.format("got key: %s", youTubeid));
            }
            catch (JSONException e){
                logError("Failed to get youtube id", e, true); }
        }
        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            logError("Request from now_playing failed", throwable, true);
        }
    });
}

    public void onTrailer(View view){
        Intent intent = new Intent(this, MovieTrailerActivity.class);
        intent.putExtra(place, youTubeid);
        this.startActivity(intent);
    }



    //handle errors, log and alert the user
    //like helper method
    private void logError(String message, Throwable e, boolean alertUser){
        //always log error for developer
        Log.e(TAG, message, e);
        //alert the user using a toast
        if (alertUser){
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
};
