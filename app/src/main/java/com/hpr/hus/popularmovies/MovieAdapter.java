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
    private String[] movieData;
    private  Context mContext;
    private final MovieSelected[] mMovies;
    private  MovieAdapterOnClickHandler movieClickHandler;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, MovieSelected[] movies) {
        //mContext = context;
        mMovies = movies;
        movieClickHandler = clickHandler;
        Log.v("hhhh", "MovieAdapter");

    }
    public MovieAdapter(MovieSelected[] movies, Context context) {
        mContext = context;
        mMovies = movies;

        Log.v("hhhh", "MovieAdapter222");
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // This method is not running in creation of the class.
        Log.v("hhhh", "MovieAdapter- onCreateViewHolder");
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movielistitem;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        Log.v("tttttttt","viewType:  " + viewType);

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ImageView imageView;

        // Will be null if it's not recycled. Will initialize ImageView if new.
        //  if (convertView == null) {
        imageView = new ImageView(context);
        imageView.setAdjustViewBounds(true);
        Log.v("ttt33","view:  " + imageView.toString());
        // } else {
        //     imageView = (ImageView) convertView;
        //  }

        Picasso.with(mContext)
                .load(mMovies[viewType].getPosterPath())
                .resize(mContext.getResources().getInteger(R.integer.tmdb_poster_w185_width),
                        mContext.getResources().getInteger(R.integer.tmdb_poster_w185_height))
                .into(imageView);

        return new MovieAdapterViewHolder(imageView);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

 @Override
 public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
     String movieForThisSelection = movieData[position];
     holder.mMovieTextView.setText(movieForThisSelection);
 }




    public interface MovieAdapterOnClickHandler {
        void onClick(String movieSelected);

    }
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mMovieTextView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieTextView = (TextView) view.findViewById(R.id.tv_movie_data);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String selectedMovie = movieData[adapterPosition];
            movieClickHandler.onClick(selectedMovie);
        }
    }


    public int getItemCount() {/////////
        if (null == movieData) return 0;
        return movieData.length;
    }
}
