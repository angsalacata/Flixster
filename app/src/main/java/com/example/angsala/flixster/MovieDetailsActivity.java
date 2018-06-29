package com.example.angsala.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.angsala.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    //retrieve, unwrap and assign this field in the onCreate method
    Movie movie;
    //Track Textview fields for txtvTitle, txtvOverview textview for new activity for movie details activity
    TextView txtvTitle;
    TextView txtvOverview;
    //Track RatingBar object rbVotingAverage
    RatingBar rbVotingAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
//The java.lang.Class.getSimpleName() returns the simple name of the underlying class as given in the source code. Returns an empty string if the underlying class is anonymous.

     txtvTitle = (TextView) findViewById(R.id.txtvTitle);
     txtvOverview = (TextView) findViewById(R.id.txtvOverview);
     rbVotingAverage = (RatingBar) findViewById(R.id.rbVotingAverage);

//unwrap the movie passed in via intent, using its simple name as the key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        //Log it
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        //set the title and overview
        txtvTitle.setText(movie.getTitle());
        txtvOverview.setText(movie.getOverview());

        //set the ratings bar with scale of 0-10 divide by two
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVotingAverage.setRating(voteAverage/2);// = voteAverage > 0 ? voteAverage/2.0f : voteAverage);

        //IDEAS FOR OTHER TEXT FIELDS: POPULARITY AND ADULT TAG- need to know how to cast text view into string to look for "true or false"
    }
}
