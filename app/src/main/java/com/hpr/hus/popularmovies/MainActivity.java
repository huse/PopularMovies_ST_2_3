package com.hpr.hus.popularmovies;

/**
 * Created by hk640d on 8/1/2017.
 */

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.hpr.hus.popularmovies.MovieAdapter.MovieAdapterOnClickHandler;


public class MainActivity extends AppCompatActivity implements MovieAdapterOnClickHandler {

    private RecyclerView rvList;
    MovieAdapter movieAdapter;
    //private final String LOG_TAG = MainActivity.class.getSimpleName();

    //private TextView mErrorMessageDisplay;
    MovieAdapterOnClickHandler clickHandler;


    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("hhhh", "MainActivity_onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reserveInitialingViews(savedInstanceState);
        rvList = (RecyclerView) findViewById(R.id.recyclerview_movies_list);
        //empty initial RecyclerView
        MovieSelected[] movies = new MovieSelected[0];
        clickHandler = this;
        rvList.setAdapter(new MovieAdapter(movies,getApplicationContext(),this));
    }
    private void initialingViews(Parcelable[] parcelable){
    /*LinearLayoutManager layoutManager
            = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);*/
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvList.setLayoutManager(layoutManager);
        rvList = (RecyclerView) findViewById(R.id.recyclerview_movies_list);
       // rvList.setHasFixedSize(true);
        rvList.setVisibility(View.VISIBLE);
        Log.v("hhhh", "MainActivity_onCreate_rvList.setAdapter");
        setContentView(R.layout.activity_main);

        Log.v("hhhh", "MainActivity_onCreate_parcelable");
//            if (parcelable != null) {
        int numMovieObjects = parcelable.length;
        MovieSelected[] movies = new MovieSelected[numMovieObjects];
        for (int i = 0; i < numMovieObjects; i++) {
            movies[i] = (MovieSelected) parcelable[i];
        }
       // rvList.setAdapter(new MovieAdapter(movies,getApplicationContext()));

    }
    private void reserveInitialingViews(@Nullable Bundle savedInstanceState){
        rvList = (RecyclerView) findViewById(R.id.recyclerview_movies_list);
        // mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);
       // rvList.setHasFixedSize(true);

        rvList.setVisibility(View.VISIBLE);

        //Log.v("hhhh-savedInstanceState", savedInstanceState.toString());
        if (savedInstanceState == null) {
            Log.v("hhhh", "MainActivity_onCreate_savedInstanceState == null");
            //here should be edited
            //ToDo edit this line
            getMoviesFromTMDb(getSortMethod());
        } else {

            Parcelable[] parcelable = savedInstanceState.
                    getParcelableArray(getString(R.string.parcel_movie));
            Log.v("hhhh", "MainActivity_onCreate_parcelable");
            if (parcelable != null) {
                int numMovieObjects = parcelable.length;
                MovieSelected[] movies = new MovieSelected[numMovieObjects];
                for (int i = 0; i < numMovieObjects; i++) {
                    movies[i] = (MovieSelected) parcelable[i];
                }
                Log.v("hhhh", "MainActivity_onCreate_rvList.setAdapter");

               // rvList.setAdapter(new MovieAdapter(movies,getApplicationContext()));
            }
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, mMenu);


        mMenu = menu;


        mMenu.add(Menu.NONE,
                R.string.pref_sort_pop_desc_key,
                Menu.NONE,
                null)
                .setVisible(false)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);


        mMenu.add(Menu.NONE, R.string.pref_sort_top_rated_desc_key, Menu.NONE, null)
                .setVisible(false)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);


        updateMenu();

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.string.pref_sort_pop_desc_key:
                updateSharedPrefs(getString(R.string.tmdb_sort_pop_desc));
                updateMenu();
                getMoviesFromTMDb(getSortMethod());
                Log.v("kkkkkk","pref_sort_pop_desc_key");
                return true;
            case R.string.pref_sort_top_rated_desc_key:
                updateSharedPrefs(getString(R.string.tmdb_sort_vote_avg_desc));
                updateMenu();
                getMoviesFromTMDb(getSortMethod());
                Log.v("kkkkkk","pref_sort_vote_avg_desc_key");
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }


    private final GridView.OnItemClickListener moviePosterClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MovieSelected movie = (MovieSelected) parent.getItemAtPosition(position);

            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(getResources().getString(R.string.parcel_movie), movie);

            startActivity(intent);
        }
    };



    private void getMoviesFromTMDb(String sortMethod) {
        if (isNetworkAvailable()) {
            Log.v("hhhh2","NetworkAvailable");
            String apiKey = getString(R.string.key_themoviedb);
           // mErrorMessageDisplay.setVisibility(View.INVISIBLE);

            // mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
            /*LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);*/
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvList.setLayoutManager(layoutManager);
            rvList.setHasFixedSize(true);
            rvList.setVisibility(View.VISIBLE);

            TaskInterfaceCompleted taskCompleted = new TaskInterfaceCompleted() {
                @Override
                public void onFetchMoviesTaskCompleted(MovieSelected[] movies) {
                    String movieName="";
                    for (MovieSelected ms : movies){
                        movieName = movieName + " _ " + ms.getOriginalTitle();
                    }
                    Log.v("hhhh2_this", this.getClass().toString());
                    Log.v("hhhh2_getApplic", getApplicationContext().getClass().toString());

                    movieAdapter = new MovieAdapter(movies,getApplicationContext(),clickHandler);

                    rvList.setAdapter(movieAdapter);
                    Log.v("hhhh2",movieName);
                }
            };


            AsyncTaskFetchPopularMovies movieTask = new AsyncTaskFetchPopularMovies(taskCompleted, apiKey);
            movieTask.execute(sortMethod);
        } else {
            Log.v("gggg","NOT-----------NetworkAvailable");
            Toast.makeText(this, getString(R.string.error_need_internet), Toast.LENGTH_LONG).show();
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void updateMenu() {
        String sortMethod = getSortMethod();

        if (sortMethod.equals(getString(R.string.tmdb_sort_pop_desc))) {
            Log.v("hhhh2","tmdb_sort_pop_desc");
            mMenu.findItem(R.string.pref_sort_pop_desc_key).setVisible(false);
            mMenu.findItem(R.string.pref_sort_top_rated_desc_key).setVisible(true);
        } else {
            Log.v("hhhh2","pref_sort_vote_avg_desc_key");
            mMenu.findItem(R.string.pref_sort_top_rated_desc_key).setVisible(false);
            mMenu.findItem(R.string.pref_sort_pop_desc_key).setVisible(true);
        }
    }


    private String getSortMethod() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String result = prefs.getString(getString(R.string.pref_sort_method_key),
                getString(R.string.tmdb_sort_pop_desc));


        Log.v("getSortMethod", result);
        return result;
    }


    private void updateSharedPrefs(String sortMethod) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.pref_sort_method_key), sortMethod);
        editor.apply();
    }
    @Override
    public void onClick(MovieSelected movie) {

        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra(getResources().getString(R.string.parcel_movie), movie);

        startActivity(intent);

    }

}
