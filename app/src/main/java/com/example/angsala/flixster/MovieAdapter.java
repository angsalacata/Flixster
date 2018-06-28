package com.example.angsala.flixster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angsala.flixster.models.Config;
import com.example.angsala.flixster.models.Movie;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    //will wrap the list of movies
    ArrayList<Movie> movies;

    //new Config for glide
    Config config;

    //context for rendering, so it is available for adapter
    Context context;


    //getters and setters for setting MovieAdapter's config
    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    //initialize with the list, with constructor
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    //creates and inflates a new view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        //create the inflater, get context from the parent first
         context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create the view using the item_movie layout and from inflater
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        // return a view movieView wrapped in a new ViewHolder
        return new ViewHolder(movieView);
    }

    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get movie data at the specified position
        Movie movie = movies.get(position);
        //populate the view with the movie data
        //NOTICE this is accessing the item_movie class
        holder.txtvTitle.setText(movie.getTitle());
        holder.txtvOverview.setText(movie.getOverview());

        //create url with config. Use the config to get the posterSize and the movie PosterPath
        String imageUrl = config.getimageURL(config.getPosterSize(), movie.getPosterPath());

        //loading image with GlideApp
        GlideApp.with(context)
                .load(imageUrl)
                .transform(new RoundedCornersTransformation(15,5))
                //remember this is how we access resources in JAVA, via R file
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(holder.imvPosterImage);

    }

    // returns total number of items in the list
    @Override
    public int getItemCount() {
        //this is the size of the movies arraylist- don't return 0!
        return movies.size();
    }

    //create viewHolder class as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //track view objects, found in item_movie XML
        ImageView imvPosterImage;
        TextView txtvTitle;
        TextView txtvOverview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //look up objects by id
            //look up poster
            imvPosterImage = (ImageView)itemView.findViewById(R.id.imvPosterImage);
            //look up title-R looks at XML!
            txtvTitle = (TextView)itemView.findViewById(R.id.txtvTitle);
            //look up overview- R looks at XML!
            txtvOverview = (TextView)itemView.findViewById(R.id.txtvOverview);
        }
    }

}
