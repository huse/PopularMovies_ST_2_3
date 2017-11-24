package com.hpr.hus.popularmovies;

/**
 * Created by hk640d on 8/1/2017.
 */

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.hpr.hus.popularmovies.MovieAdapter.MovieAdapterOnClickHandler;
import com.hpr.hus.popularmovies.db.MovieListContract;
import com.hpr.hus.popularmovies.settings_2.SettingsActivity;

import java.util.Map;


public class MainActivity extends AppCompatActivity implements MovieAdapterOnClickHandler,SharedPreferences.OnSharedPreferenceChangeListener ,   LoaderManager.LoaderCallbacks<Cursor>  {

    private RecyclerView rvList;
    MovieAdapter movieAdapter;
    /// for fav functionality

    private String mPosterPath;
    private String mOverview;
    private Double mVoteAverage;
    private String mReleaseDate;

    private long mId;
    private String mTitle;
    private long mVoteCount;
    private boolean mIsFavMovie;
    private String mBackdrop;
    private Object[] mMovieArray;
    ///
    MovieAdapterOnClickHandler clickHandler;
    private static final String TAG = MainActivity.class.getSimpleName();

    private Menu mMenu;
    private Cursor mData;
    private int mIdCol, mFavCol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("hhhh", "MainActivity_onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reserveInitialingViews(savedInstanceState);
        rvList = (RecyclerView) findViewById(R.id.recyclerview_movies_list);


        MovieSelected[] movies = new MovieSelected[0];
        clickHandler = this;
        rvList.setAdapter(new MovieAdapter(movies,getApplicationContext(),this));
        setupSharedPreferences();




    }

    private void reserveInitialingViews(@Nullable Bundle savedInstanceState){
        rvList = (RecyclerView) findViewById(R.id.recyclerview_movies_list);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);


        rvList.setVisibility(View.VISIBLE);


        if (savedInstanceState == null) {
            Log.v("hhhh", "MainActivity_onCreate_savedInstanceState == null");
          //  new FavFetchTask().execute();
           // Log.v("yyy61", mData + "");
            ContentResolver resolver = getContentResolver();
            Log.v("hhhh6", "DetailActivity doInBackground 6962");
            // Call the query method on the resolver with the correct Uri from the contract class
            mData = resolver.query(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI,
                    null, null, null, MovieListContract.MoviesEntry.MOVIE_ID);
            Log.v("yyy61", mData + "");
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

            }
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        mMenu = menu;


        mMenu.add(Menu.NONE,
                R.id.sort_popularity,
                Menu.NONE,
                null)
                .setVisible(true)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        mMenu.add(Menu.NONE, R.id.sort_top_rate, Menu.NONE, null)
                .setVisible(false)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        //updateMenu();

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

           /* case R.id.sort_popularity:
               // updateSharedPrefs(getString(R.string.tmdb_sort_pop_desc));

                getMoviesFromTMDb(getSortMethod());
                Log.v("kkkkkk","pref_sort_pop_desc_key");
                mMenu.findItem(R.id.sort_popularity).setVisible(false);
                mMenu.findItem(R.id.sort_top_rate).setVisible(true);
                updateMenu();
                return true;
            case R.id.sort_top_rate:
              //  updateSharedPrefs(getString(R.string.tmdb_sort_vote_avg_desc));

                getMoviesFromTMDb(getSortMethod());
                mMenu.findItem(R.id.sort_popularity).setVisible(true);
                mMenu.findItem(R.id.sort_top_rate).setVisible(false);
                Log.v("kkkkkk","pref_sort_vote_avg_desc_key");
                updateMenu();
                return true;*/
            case R.id.settings:
                Log.v("nnnn","MainActivity - R.id.settings 1");
                Intent intent = new Intent(this, SettingsActivity.class);
                Log.v("nnnn","MainActivity - R.id.settings 2");
                startActivity(intent);

                Log.v("nnnn","MainActivity - R.id.settings 3");
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



    private void getMoviesFromTMDb(String sortValueFromSortMethod) {
        if (isNetworkAvailable()) {
            Log.v("hhhh2","NetworkAvailable");
            String apiKey = getString(R.string.key_themoviedb);

            rvList.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));

            rvList.setHasFixedSize(true);
            rvList.setVisibility(View.VISIBLE);

            TaskInterfaceCompleted taskCompleted = new TaskInterfaceCompleted() {
                @Override
                public void onFetchMoviesTaskCompleted(MovieSelected[] movies) {
                    String movieName="";
                    if (movies!=null && movies.length!=0) {
                        for (MovieSelected ms : movies) {

                            movieName = movieName + " _ " + ms.getOriginalTitle();
                            Log.v("hhhh2_onFetchMoviesTask", movieName+"");
                        }
                        Log.v("hhhh2_this", this.getClass().toString());
                        Log.v("hhhh2_getApplic", getApplicationContext().getClass().toString());

                        movieAdapter = new MovieAdapter(movies, getApplicationContext(), clickHandler);

                        rvList.setAdapter(movieAdapter);
                        Log.v("hhhh2", movieName);
                    }else {

                       // new FavFetchTask().execute();
                        //MovieSelected[] movieSelecteds = new MovieSelected(mMovieArray );

                       // movieAdapter = new MovieAdapter(movieSelecteds, getApplicationContext(), clickHandler);

                      //  rvList.setAdapter(movieAdapter);

                        Toast.makeText(MainActivity.this, "No Movie to show", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onDbFavTaskCompeted() {

                }
            };
          //  new FavFetchTask().execute();
            Log.v("yyy51", mData + "");
                if (sortValueFromSortMethod.equals("popular")|| sortValueFromSortMethod.equals("top_rated")){
            AsyncTaskFetchPopularMovies movieTask = new AsyncTaskFetchPopularMovies(taskCompleted, apiKey);
            movieTask.execute(sortValueFromSortMethod);
                } else if(sortValueFromSortMethod.equals("favorite_movies")&& mData!=null){

            AsyncTaskFetchFavorite movieFavTask = new AsyncTaskFetchFavorite(taskCompleted, mData);
            movieFavTask.execute(sortValueFromSortMethod);
                }
           // new FavFetchTask().execute();
        } else {
            Log.v("gggg","NO-----------NetworkAvailable");
            Toast.makeText(this, getString(R.string.error_need_internet), Toast.LENGTH_LONG).show();
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


/*    private void updateMenu() {
        String sortMethod = getSortMethod();
        Log.v("BBBBBBBBBBBBB","MainActivity - updateMenu   " + sortMethod);
        if (sortMethod.equals(getString(R.string.tmdb_sort_pop_desc))) {
            Log.v("hhhh2","tmdb_sort_pop_desc");



            mMenu.findItem(R.id.sort_popularity).setVisible(false);
            mMenu.findItem(R.id.sort_top_rate).setVisible(true);

        } else {
            Log.v("hhhh2","pref_sort_vote_avg_desc_key");

            mMenu.findItem(R.id.sort_popularity).setVisible(true);
            mMenu.findItem(R.id.sort_top_rate).setVisible(false);
        }
    }*/


    private String getSortMethod() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String result = prefs.getString(getString(R.string.preference_sorting_key),

                getString(R.string.tmdb_sort_pop_desc));
        Log.v("BBBBBBBBBBBBB666666666","MainActivity - getSortMethod   " + result);

        Log.v("getSortMethod", result);
        return result;
    }


   private void updateSharedPrefs(String sortMethod) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.preference_sorting_key), sortMethod);
        editor.apply();
    }
    @Override
    public void onClick(MovieSelected movie) {

        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra(getResources().getString(R.string.parcel_movie), movie);

        startActivity(intent);

    }
    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.v("FFFFFFFFFFFF","MainActivity - onSharedPreferenceChanged   " + key);
        loadSortFromPreferences(sharedPreferences);


    }
    private void setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        SharedPreferences sharedPreferences = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
//        updateMenu();
        loadSortFromPreferences(sharedPreferences);
        //loadColorFromPreferences(sharedPreferences);
        // Register the listener
        Log.v("FFFFFFFFFFFF","MainActivity - setupSharedPreferences   " + sharedPreferences.toString());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }
    private void loadSortFromPreferences(SharedPreferences sharedPreferences) {

         Log.v("FFFFFFFFFFFF0","MainActivity - loadSortFromPreferences   " + sharedPreferences.toString());
         //Log.v("FFFFFFFFFFFF1","MainActivity - loadSortFromPreferences   " + getString(R.string.pref_sort_method_key));
        // Log.v("FFFFFFFFFFFF2","MainActivity - loadSortFromPreferences   " + getSortMethod());
         Log.v("FFFFFFFFFFFF3","MainActivity - loadSortFromPreferences   " + getString(R.string.tmdb_sort_pop_desc));
         Log.v("FFFFFFFFFFFF4","MainActivity - loadSortFromPreferences   " + sharedPreferences.getString((getString(R.string.preference_sorting_key)),getString(R.string.tmdb_sort_pop_desc) ));

         /*SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.putString(getString(R.string.pref_sort_method_key),
                 sharedPreferences.getString((getString(R.string.pref_sort_method_key)),getString(R.string.tmdb_sort_pop_desc) ));
         editor.apply();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);*/
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }

        updateSharedPrefs(sharedPreferences.getString((getString(R.string.preference_sorting_key)),getString(R.string.tmdb_sort_pop_desc) ));


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<Cursor>(this) {


            Cursor mMovieDataCursor = null;


            @Override
            protected void onStartLoading() {
                if (mMovieDataCursor != null) {

                    deliverResult(mMovieDataCursor);
                } else {

                    forceLoad();
                }
            }


            @Override
            public Cursor loadInBackground() {

                try {
                    return getContentResolver().query(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieListContract.MoviesEntry.MOVIE_FAVORED);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }


            public void deliverResult(Cursor data) {
                mMovieDataCursor = data;
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
/*    public void setSort(String sorting){

        switch (sorting) {

            case getString(R.string.tmdb_sort_pop_desc):
                 updateSharedPrefs(getString(R.string.tmdb_sort_pop_desc));

                getMoviesFromTMDb(getSortMethod());
                Log.v("kkkkkk", "pref_sort_pop_desc_key");

            case getString(R.string.tmdb_sort_vote_avg_desc):
                  updateSharedPrefs(getString(R.string.tmdb_sort_vote_avg_desc));

                getMoviesFromTMDb(getSortMethod());

        }
    }*/

    public class FavFetchTask extends AsyncTask<Void, Void, Cursor> {

        // Invoked on a background thread



        @Override
        protected Cursor doInBackground(Void... params) {
            // Make the query to get the data
            Log.v("hhhh6", "DetailActivity doInBackground 696");

            // Get the content resolver
            ContentResolver resolver = getContentResolver();
            Log.v("hhhh6", "DetailActivity doInBackground 6962");
            // Call the query method on the resolver with the correct Uri from the contract class
            Cursor cursor = resolver.query(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI,
                    null, null, null, MovieListContract.MoviesEntry.MOVIE_ID);
            Log.v("hhhh6", "DetailActivity doInBackground 6964");
            Log.v("yyy31", mData + "");
            mData = cursor;
            Log.v("yyy32", mData + "");
            return cursor;
        }


        // Invoked on UI thread
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            mData = cursor;

            mIdCol = mData.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_ID);
            mFavCol = mData.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_FAVORED);

            Log.v("yyy45", mData + "");
            Log.v("yyy46", mIdCol + "");
            Log.v("yyy47", mFavCol + "");


           // nextMovie();
        }

        public void nextMovie() {
            if (mData != null) {

                try {
                    int counter=0;
                    boolean bool=true;

                    while (mData.moveToNext()&& bool) {

                        counter++;

                        /*if (Integer.parseInt(mData.getString(mIdCol)) == currentMovie.getId()) {
                            favStatus= mData.getString(mFavCol).equals("1");
                            favoriteButton.setSelected(favStatus);
                            Log.v("yyy366", "Compare IDs : " + mData.getString(1)+"  vs  "+ currentMovie.getId());
                            Log.v("yyy366", "mFavCol value is: " + mFavCol+"");
                            Log.v("yyy366", "mFavCol string is: " + favStatus +"");


                            movieExistsOnDB = true;
                            bool =false;
                        }*/
                        Log.v("yyy38_counter", counter +"");
                        Log.v("yyy38", mData.getString(0)+ " _ "+ mData.getString(1)+ " _ "+
                                mData.getString(2)+ " _ "+
                                mData.getString(3)+ " _ "+
                                mData.getString(4)+ " _ "+
                                mData.getString(5)+ " _ "+
                                mData.getString(6)+ " _ "+
                                mData.getString(7)+ " _ "+
                                mData.getString(8)+ " _ "+
                                mData.getString(9));
                        Log.v("yyy39", mData.getString(mIdCol));
                        Log.v("yyy391", mData.getString(mFavCol).equals("1") + "");
                        mPosterPath = mData.getString(7);
                        mOverview = mData.getString(3);
                        mVoteAverage = Double.parseDouble(mData.getString(4));
                        mReleaseDate = mData.getString(8);

                        mId = Long.parseLong(mData.getString(1));
                        mTitle = mData.getString(2);
                        mVoteCount = Long.parseLong(mData.getString(5));
                        mIsFavMovie = mData.getString(9).equals("1");
                        mBackdrop = mData.getString(6);
                        Object[]  tempArray = {mId,  mTitle, mPosterPath,
                                mVoteAverage,  mVoteCount, mReleaseDate,
                                mBackdrop,  mIsFavMovie};
                        mMovieArray[counter]=tempArray;
                       // Parcel mParcel =


                    }


                } finally {
                    mData.moveToFirst();
                    // mData.close();
                }
            }else {
                mData.moveToFirst();
            }

        }



    }
}
