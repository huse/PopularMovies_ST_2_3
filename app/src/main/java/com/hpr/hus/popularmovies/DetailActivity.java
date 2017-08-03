package com.hpr.hus.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #PopularMoviesApp";

    private String sMovie;
    private TextView movieDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieDisplay = (TextView) findViewById(R.id.tv_display_movie);

        Intent intentThatStartedThisActivity = getIntent();

        // COMPLETED (2) Display the weather forecast that was passed from MainActivity
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                sMovie = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                movieDisplay.setText(sMovie);
            }
        }
    }
}
