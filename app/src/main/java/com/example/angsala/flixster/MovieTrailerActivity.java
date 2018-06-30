package com.example.angsala.flixster;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static com.example.angsala.flixster.MovieDetailsActivity.place;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);


        final String videoID = getIntent().getStringExtra(place);

        //temporary video id - to be replaced by real movie trailer id


        //resolve the player view from the layout. The type of view needed here is YouTubePlayerView
        YouTubePlayerView player = (YouTubePlayerView) findViewById(R.id.player);

        player.initialize(getString(R.string.YTapi_key), new YouTubePlayer.OnInitializedListener() {
            @Override

            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //this is the work to play up the youtube trailer video
                //cue video not playlist lol
                youTubePlayer.cueVideo(videoID);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.i("MovieTrailerActivity", "Error to initialize YoutubePlayer view");
            }
        });
    }
}
