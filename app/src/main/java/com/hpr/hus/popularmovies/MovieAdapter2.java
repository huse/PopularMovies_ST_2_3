package com.hpr.hus.popularmovies;

/**
 * Created by hk640d on 8/1/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MovieAdapter2 extends RecyclerView.Adapter<MovieAdapter2.MovieAdapterViewHolder> {
    private String[] movieData;
    public MovieAdapter2(MovieAdapterOnClickHandler clickHandler) {
        movieClickHandler = clickHandler;
    }

    private final MovieAdapterOnClickHandler movieClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(String movieSelected);

    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String movieForThisSelection = movieData[position];
        holder.mWeatherTextView.setText(movieForThisSelection);
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movielistitem;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView mWeatherTextView;





    public MovieAdapterViewHolder(View view) {
        super(view);
        mWeatherTextView = (TextView) view.findViewById(R.id.tv_movie_data);
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
        String weatherForDay = movieData[adapterPosition];
        movieClickHandler.onClick(weatherForDay);
    }
}


    public int getItemCount() {
        if (null == movieData) return 0;
        return movieData.length;
    }

}
