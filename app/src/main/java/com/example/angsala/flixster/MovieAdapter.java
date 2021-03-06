package com.example.angsala.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angsala.flixster.models.Config;
import com.example.angsala.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    //will wrap the list of movies
    ArrayList<Movie> movies;

    //new Config for glide
    Config config;

    //context for rendering, so it is available for adapter
    Context context;

    public final static String placeholder = "https://api.themoviedb.org/3";

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

        //determine the current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        //create url with config. Use the config to get the posterSize and the movie PosterPath
        String imageUrl = null;
        //change url, if portrait mode load the poster image, else load backdrop image
        if (isPortrait){
            imageUrl = config.getimageURL(config.getPosterSize(), movie.getPosterPath());}
            else {
             imageUrl = config.getimageURL(config.getBackdropSize(), movie.getBackdropPath());}


//change Glide arguments, depending on orientation, with ternary operator
        int placeHolderNum = isPortrait ? R.drawable.flicks_movie_placeholder: R.drawable.flicks_backdrop_placeholder;

        ImageView image_orientation = isPortrait ? holder.imvPosterImage : holder.imvBackdropImage;
        //loading image with GlideApp
        GlideApp.with(context)
                .load(imageUrl)
                .transform(new RoundedCornersTransformation(15,5))
                //remember this is how we access resources in JAVA, via R file
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(image_orientation);


    }

    // returns total number of items in the list
    @Override
    public int getItemCount() {
        //this is the size of the movies arraylist- don't return 0!
        return movies.size();
    }

    //create viewHolder class as a static inner class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //track view objects, found in item_movie XML
        ImageView imvPosterImage;
        TextView txtvTitle;
        TextView txtvOverview;
        //Track view object for landscape orientation
        ImageView imvBackdropImage;

//view holder constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //look up objects by id
            //look up poster
            imvPosterImage = (ImageView)itemView.findViewById(R.id.imvPosterImage);
            //look up title-R looks at XML!
            txtvTitle = (TextView)itemView.findViewById(R.id.txtvTitle);
            //look up overview- R looks at XML!
            txtvOverview = (TextView)itemView.findViewById(R.id.txtvOverview);

            //look up imvBackdropImage- R looks at XML
            imvBackdropImage = (ImageView)itemView.findViewById(R.id.imvBackdropImage);

            //added as the itemView's onClickListener
            itemView.setOnClickListener(this);
        }

        //implemented, added from implements method. adeed to get ratings and details

        //We use onClick because in the viewHolder class above, put in "implements View.OnClickListener"
        @Override
        public void onClick(View view) {
            int viewPosition = getAdapterPosition();
            //check if position exists in the Recycler View
            if (viewPosition != RecyclerView.NO_POSITION){
                Movie movie = movies.get(viewPosition);
                //create new intent for new activity, we use .class, needed because we don't have a MovieDetailsActivity instanced yet
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                //serialize (?) movie with parceler, using short name as key, not sure what this one does, other than
                //it will connect the click to the new MovieDetailsActivity activity
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                //to put a backdrop in the activities view
                intent.putExtra(placeholder, config.getimageURL(config.getBackdropSize(), movie.getBackdropPath()));
                //show this new MovieDetailsActivity activity
                //We use context here because we are in an adapter, whereas startActivity() can be used by itself when
                context.startActivity(intent);

            }

        }
    }

}
