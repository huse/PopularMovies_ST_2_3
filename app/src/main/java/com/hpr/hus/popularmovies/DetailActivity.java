package com.hpr.hus.popularmovies;

/**
 * Created by hk640d on 8/1/2017.
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;

public class DetailActivity extends AppCompatActivity {

    private static final String SHARE_HASHTAG = " #PopularMoviesApp";

    private String sMovie;
    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    private TextView movieDisplay;

    TextView originalTitleTV;
    TextView overViewTV;
    TextView rateAverageTV;
    TextView releaseTimeTV;
    ImageView imagePosterIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.v("hhhh6", "DetailActivity-onCreate");
       // movieDisplay = (TextView) findViewById(R.id.tv_display_movie);
        originalTitleTV = (TextView) findViewById(R.id.textview_original_title);
        overViewTV = (TextView) findViewById(R.id.textview_overview);
        rateAverageTV = (TextView) findViewById(R.id.textview_rate_average);
        releaseTimeTV = (TextView) findViewById(R.id.textview_release_time);
        imagePosterIV = (ImageView) findViewById(R.id.imageview_image_poster);

        Intent intentThatStartedThisActivity = getIntent();
        Log.v("hhhh6", "intentThatStartedThisActivity    "+intentThatStartedThisActivity.toString());

/// TODO: 8/19/2017 fix  movie null bug //done
        MovieSelected movie;
        if (intentThatStartedThisActivity != null) {

                movie = intentThatStartedThisActivity.getExtras().getParcelable("PARCEL_MOVIE");
                Log.v("hhhh6", "movie   check point");
                //Checking to see if the movie is null:
                Log.v("hhhh6", "movie   " + movie.toString());

               originalTitleTV.setText(movie.getOriginalTitle());

                Picasso.with(this)
                        .load(movie.getPosterPath())
                        .resize(getResources().getInteger(R.integer.tmdb_poster_w185_width),
                                getResources().getInteger(R.integer.tmdb_poster_w185_height))
                        .into(imagePosterIV);

                String overView = movie.getOverview();

                if (overView == null) {
                    overViewTV.setTypeface(null, Typeface.ITALIC);
                    overView = getResources().getString(R.string.no_summary_found);
                }
                overViewTV.setText(overView);
                rateAverageTV.setText(movie.getDetailedVoteAverage());

                // First get the release date from the object - to be used if something goes wrong with
                // getting localized release date (catch).
                String releaseDate = movie.getReleaseDate();

             /*   if (releaseDate != null) {
                    try {
                        releaseDate = DateAdding.getLocalizedDate(this,
                                releaseDate, movie.getDateFormat());
                       // releaseDate = DateAdding.releaseDate);
                    } catch (ParseException e) {
                        Log.e(LOG_TAG, "Error with parsing movie release date", e);
                    }
                } else {
                    releaseTimeTV.setTypeface(null, Typeface.ITALIC);
                    releaseDate = getResources().getString(R.string.no_release_date_found);
                }*/
                releaseTimeTV.setText(releaseDate);
            }
    }
}
