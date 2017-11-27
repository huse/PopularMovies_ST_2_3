package com.hpr.hus.popularmovies.review_holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hpr.hus.popularmovies.R;
import com.hpr.hus.popularmovies.video_holders.AdaptorVideos;

/**
 * Created by hk640d on 11/24/2017.
 */

public class HolderReviews extends RecyclerView.ViewHolder {


    private final View viewIt;
    private TextView authorTextView;
    private TextView reviewTextView;

    HolderReviews(View itemView) {

        super(itemView); Log.v("review1", "HolderReviews");
        viewIt = itemView;
        authorTextView =  viewIt.findViewById(R.id.author_review);
        reviewTextView = viewIt.findViewById(R.id.content_review);
    }

    void setAuthorsName(String Authors) {
        Log.v("review1", "setAuthorsName" + Authors);
        authorTextView.setText(Authors);
    }

    void setReviews(String review) {
        Log.v("review1", "setAuthorsName" + review);
        reviewTextView.setText(review);
    }


}
