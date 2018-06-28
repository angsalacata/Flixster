package com.example.angsala.flixster;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angsala.flixster.models.Movie;

import java.util.ArrayList;
//what here is the superclass exactly?
//where do these methods come from? Is it inherited by RecyclerView.Adapter?
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    //will wrap the list of movies
    ArrayList<Movie> movies;

    //initialize with the list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
