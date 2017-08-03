package com.hpr.hus.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

//some changes for vcs
public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView rvList;
    private MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvList= (RecyclerView) findViewById(R.id.recyclerview_movies_list);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvList.setLayoutManager(layoutManager);
        rvList.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this);
        rvList.setHasFixedSize(true);
       // rvList.setAdapter();
        rvList.setHasFixedSize(true);
    }

    @Override
    public void onClick(String movieSelected) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        // COMPLETED (1) Pass the weather to the DetailActivity
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movieSelected);
        startActivity(intentToStartDetailActivity);
    }
}
