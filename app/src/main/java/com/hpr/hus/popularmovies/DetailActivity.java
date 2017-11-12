package com.hpr.hus.popularmovies;

/**
 * Created by hk640d on 8/1/2017.
 */

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hpr.hus.popularmovies.db.MovieListContract;
import com.hpr.hus.popularmovies.db.MovieListDBhelper;
import com.hpr.hus.popularmovies.db.MovieListProvider;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mUri;
    private SQLiteDatabase myDb;
    private MovieSelected currentMovie;
    private static final int ID_DETAIL_LOADER = 596;
    private static final String TAG = DetailActivity.class.getSimpleName();
    @BindView(R.id.textview_original_title) TextView originalTitleTV;
    @BindView(R.id.textview_overview) TextView overViewTV;
    @BindView(R.id.textview_rate_average) TextView rateAverageTV;
    @BindView(R.id.textview_release_time) TextView releaseTimeTV;
    @BindView(R.id.imageview_image_poster) ImageView imagePosterIV;
    @BindView(R.id.fav_button) ImageButton favoriteButton;
    private Cursor mData;
    private int mIdCol, mFavCol;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.v("hhhh6", "DetailActivity-onCreate");

        MovieListProvider movieListProvider = new MovieListProvider();
        MovieListDBhelper dBhelper = new MovieListDBhelper(this);
        Intent intentThatStartedThisActivity = getIntent();
        Log.v("hhhh6", "intentThatStartedThisActivity    "+intentThatStartedThisActivity.toString());
        ButterKnife.bind(this);
        Log.v("hhhh6", "889");
        myDb = dBhelper.getWritableDatabase();
        Log.v("hhhh6", "890");

        MovieSelected movie;
        Log.v("hhhh6", "891");
        //currentMovie = bundle.getParcelable(MOVIE_EXTRA);

        if (intentThatStartedThisActivity != null   && !getIntent().getBooleanExtra("isNewItem", false)) {

                movie = intentThatStartedThisActivity.getExtras().getParcelable("PARCEL_MOVIE");
                Log.v("hhhh6", "movie   check point");
                //Checking to see if the movie is null:
                Log.v("hhhh6entering", "movie   " + movie.getTitle());
            currentMovie=movie;
            Log.v("hhhh6", "DetailActivity 67");
            String result=currentMovie.getFavMovie()+"";
            Log.v("hhhh6", "DetailActivity 68");
           // favoriteButton.setText(result);\
            ////at this line the fav button ger the status:
            mUri = getIntent().getData();
            Log.v("hhhh6", "DetailActivity 69");
          //  if (mUri == null) throw new NullPointerException("URI for DetailActivity cannot be null")

           // movieListProvider.query(mUri,null,null,null,null);
            Log.v("hhhh6", "DetailActivity 70");

            getMovieValues(currentMovie);
            boolean favoredBool = Boolean.valueOf(MovieListContract.MoviesEntry.MOVIE_FAVORED);
            favoriteButton.setSelected(favoredBool);
            Log.v("hhhh6_id", "movie   " + movie.getId());
           // final ContentValues values = new ContentValues();
          //  boolean favoredBool = (boolean) values.get(MovieListContract.MoviesEntry.MOVIE_FAVORED);
           // favoriteButton.setSelected(favoredBool);
           // favoriteButton.setSelected(currentMovie.getFavMovie());


            new FavFetchTask().execute();
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


                String releaseDate = movie.getReleaseDate();

                 releaseTimeTV.setText(releaseDate);
            }
        favoriteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(currentMovie.getFavMovie()==false) {
                    currentMovie.setFavMovie(true);
                    favoriteButton.setSelected(true);
                    addNewFavorite(currentMovie);
                    //Toast.makeText(DetailActivity.this, "setFavMovie(true)", Toast.LENGTH_SHORT).show();

                }else {
                    currentMovie.setFavMovie(false);
                    favoriteButton.setSelected(false);
                   // removeAFavorite(needs work);
                    // Toast.makeText(DetailActivity.this, "setFavMovie(false)", Toast.LENGTH_SHORT).show();
                }

                String result=currentMovie.getFavMovie()+"";

                Toast.makeText(DetailActivity.this, currentMovie.getFavMovie()+"", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private long addNewFavorite(MovieSelected movieSelected) {
        ContentValues cv = new ContentValues();
        cv.put(MovieListContract.MoviesEntry.MOVIE_NAME, movieSelected.getOriginalTitle());
       // cv.put(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME,  movieSelected.getTableName);
        cv.put(MovieListContract.MoviesEntry.MOVIE_ID,  movieSelected.getId());
        cv.put(MovieListContract.MoviesEntry.MOVIE_TITLE,  movieSelected.getTitle());
        cv.put(MovieListContract.MoviesEntry.MOVIE_OVERVIEW,  movieSelected.getOverview());
        cv.put(MovieListContract.MoviesEntry.MOVIE_VOTE_COUNT,  movieSelected.getVoteCount());
        cv.put(MovieListContract.MoviesEntry.MOVIE_VOTE_AVERAGE, movieSelected.getVoteAverage());
        cv.put(MovieListContract.MoviesEntry.MOVIE_RELEASE_DATE, movieSelected.getReleaseDate());
        cv.put(MovieListContract.MoviesEntry.MOVIE_FAVORED, movieSelected.getFavMovie());
        cv.put(MovieListContract.MoviesEntry.MOVIE_POSTER_PATH, movieSelected.getPosterPath());
        cv.put(MovieListContract.MoviesEntry.MOVIE_BACKDROP_PATH, movieSelected.getBackdrop());
        //cv.put(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, partySize);

        return myDb.insert(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME, null, cv);
    }
    private boolean removeAFavorite(long id) {

        return myDb.delete(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME, MovieListContract.MoviesEntry.MOVIE_ID + "=" + id, null) > 0;
    }
    public static ContentValues getMovieValues(MovieSelected movie) {
        final ContentValues values = new ContentValues();
        values.put(MovieListContract.MoviesEntry.MOVIE_ID, movie.getId());
        values.put(MovieListContract.MoviesEntry.MOVIE_TITLE, movie.getTitle());
        values.put(MovieListContract.MoviesEntry.MOVIE_OVERVIEW, movie.getOverview());
        values.put(MovieListContract.MoviesEntry.MOVIE_VOTE_COUNT, movie.getVoteCount());
        values.put(MovieListContract.MoviesEntry.MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieListContract.MoviesEntry.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieListContract.MoviesEntry.MOVIE_FAVORED, movie.getFavMovie());
        values.put(MovieListContract.MoviesEntry.MOVIE_POSTER_PATH, movie.getPosterPath());
        values.put(MovieListContract.MoviesEntry.MOVIE_BACKDROP_PATH, movie.getBackdrop());
        return values;
    }

    public  void onClickFavorite(){
        if (currentMovie == null){ Log.v("rrrrrrrrrrrr", "currentMovie == null"); return;}

        boolean favored = !currentMovie.getFavMovie();


        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieListContract.MoviesEntry.MOVIE_ID, currentMovie.getId());
        contentValues.put(MovieListContract.MoviesEntry.MOVIE_FAVORED , favored);

        Uri uri = getContentResolver().insert(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, contentValues);

        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }


        finish();

    }



    public void favoredMovieTrue(MovieSelected movie, Boolean favored) {
        setMovieTrueFavored(movie.getId(), favored);
    }
    public void setMovieTrueFavored(Long movieId, Boolean favored) {
        if (currentMovie.getId() != movieId){

            return;
        }
        currentMovie.setFavMovie(favored);
        favoriteButton.setSelected(favored);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
/*
        switch (id) {

//          COMPLETED (23) If the loader requested is our detail loader, return the appropriate CursorLoader
            case ID_DETAIL_LOADER:

                return new CursorLoader(this,
                        MovieListContract.MoivieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        MovieListContract.MoivieEntry.COLUMN_FAVORITE);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
        //*/


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


        //



    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void nextWord() {

        if (mData != null) {
            if (!mData.moveToNext()) {
                mData.moveToFirst();
            }

            favoriteButton.setSelected(Boolean.valueOf(mData.getString(mFavCol)));


        }
    }
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
                    null, null, null, MovieListContract.MoviesEntry.MOVIE_FAVORED);
            Log.v("hhhh6", "DetailActivity doInBackground 6964");
            return cursor;
        }


        // Invoked on UI thread
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            // COMPLETED (2) Initialize anything that you need the cursor for, such as setting up
            // the screen with the first word and setting any other instance variables

            //Set up a bunch of instance variables based off of the data

            // Set the data for MainActivity
            mData = cursor;
            // Get the column index, in the Cursor, of each piece of data
            mIdCol = mData.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_ID);
            mFavCol = mData.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_FAVORED);

            // Set the initial state
            nextWord();
        }
    }





   /* public void onFavoredMovie() {
        if (currentMovie == null) return;

        boolean favored = !currentMovie.getFavMovie();
        markMovieAsFavorite(currentMovie, favored);
    }

    public void setFavoredMovie(Long movieId, Boolean favored) {
        if (currentMovie.getId() != movieId) return;
        currentMovie.setFavMovie(favored);
        favoriteButton.setSelected(favored);
    }
    public void markMovieAsFavorite(MovieSelected movie, Boolean favored) {
        setMovieFavored(movie, favored).subscribe();
    }
    public Completable setMovieFavored(MovieSelected movie, boolean favored) {
        movie.setFavMovie(favored);
        return favored ? repository.saveMovie(movie) : repository.deleteMovie(movie);
    }*/
}
