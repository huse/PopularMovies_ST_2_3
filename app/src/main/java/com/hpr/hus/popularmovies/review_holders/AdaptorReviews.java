package com.hpr.hus.popularmovies.review_holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hpr.hus.popularmovies.R;

import java.util.ArrayList;

/**
 * Created by hk640d on 11/24/2017.
 */

public class AdaptorReviews extends RecyclerView.Adapter<HolderReviews> {

    private ArrayList<Reviews> reviewsArrayListList;
    @Override public int getItemCount() {
        Log.v("review1", "getItemCount  " + reviewsArrayListList.size());
        return reviewsArrayListList.size();
        // return 15;


    }
    @Override public void onBindViewHolder(HolderReviews holder, int location) {
        Log.v("review1", "onBindViewHolder");
        // position = 0;
        final Reviews review = reviewsArrayListList.get(location);
        holder.setAuthorsName(review.getAuthorsw());
        holder.setReviews(review.getContents());
    }
    public AdaptorReviews(ArrayList<Reviews> reviews) {

        this.reviewsArrayListList = reviews;
    }
  /*  public AdapterReviews(int reviews) {
        this.reviewsList = reviews;
    }*/
    @Override public HolderReviews onCreateViewHolder(ViewGroup parent, int type) {
        Log.v("review1", "onCreateViewHolder");
        Log.v("review1", "type" + type);
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_contents, null);
        return new HolderReviews(layoutView);
    }




}
