package com.hpr.hus.popularmovies;

/**
 * Created by hk640d on 8/2/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
   // private String[] movieData;
    private  Context context;
    private final MovieSelected[] movies;
   // private  MovieAdapterOnClickHandler movieClickHandler;

    public MovieAdapter(MovieSelected[] movies, Context context) {
        this.context = context;
        Log.v("hhhh4_context", context.getClass().toString());
        this.movies = movies;
        String movieName="";
        for (MovieSelected ms : movies){
            movieName = movieName + " _ " + ms.getOriginalTitle();
        }
        Log.v("hhhh4",movieName);
     //   MovieAdapterOnClickHandler clickHandler,
        //  movieClickHandler = clickHandler;
        Log.v("hhhh4", "MovieAdapter");

    }
    /*public MovieAdapter(MovieSelected[] movies, Context context) {
        this.context = context;
        this.movies = movies;

        Log.v("hhhh4", "MovieAdapter222");
    }*/

    @Override
    public MovieAdapter.MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // This method is not running in creation of the class.
        Log.v("hhhh4", "MovieAdapter- onCreateViewHolder");
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movielistitem;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        Log.v("hhhh4","viewType:  " + viewType);

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);


        return new MovieAdapterViewHolder(view);
    }

    @Override
    public long getItemId(int position) {

        Log.v("hhhh4","getItemId position  " + position );return 0;
    }

 @Override
 public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
     String movieForThisSelection = movies[position].getOriginalTitle();
     Log.v("hhhh4","onBindViewHolder_movieForThisSelection:  " + movieForThisSelection );
     holder.mMovieTextView.setText(movieForThisSelection);
     ImageView imageView;


     imageView = new ImageView(context);
     imageView.setAdjustViewBounds(true);
     Log.v("hhhh4","view:  " + imageView.toString());
//todo  put onCreateViewHolder
     //onCreateViewHolder(imageView, position);
     Log.v("hhhh4_Picasso",":  " + context.toString());
     Log.v("hhhh4_Picasso",":  " + movies.toString());
     Log.v("hhhh4_Picasso",":  " + position);
     Log.v("hhhh4_Picasso",":  " + context.getResources().getInteger(R.integer.tmdb_poster_w185_width));
     Log.v("hhhh4_Picasso",":  " + (context.getResources().getInteger(R.integer.tmdb_poster_w185_height)));
     Log.v("hhhh4_Picasso",":  " + holder.movieImageView.toString());
     Log.v("hhhh4_Picasso",":  " + context.toString());
     Picasso.with(context).load(movies[position].getPosterPath()).resize(context.getResources().getInteger(R.integer.tmdb_poster_w185_width),context.getResources().getInteger(R.integer.tmdb_poster_w185_height)).into(holder.movieImageView);
 }




    public interface MovieAdapterOnClickHandler {
        void onClick(String movieSelected);

    }
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mMovieTextView;
        ImageView movieImageView = new ImageView(context);

        public MovieAdapterViewHolder(View view) {
            super(view);
            Log.v("hhhh411","MovieAdapterViewHolder class");
            mMovieTextView = (TextView) view.findViewById(R.id.tv_movie_data);
            movieImageView = view.findViewById(R.id.imageview_image_poster);
            Log.v("hhhh4111",":  " + movieImageView.toString());
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String selectedMovie = movies[adapterPosition].getOriginalTitle();
           // movieClickHandler.onClick(selectedMovie);
        }
    }


    public int getItemCount() {/////////
        if (null == movies) return 2;
        return movies.length;
    }
}
