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
    @BindView(R.id.textview_original_title)
    TextView originalTitleTV;
    @BindView(R.id.textview_overview)
    TextView overViewTV;
    @BindView(R.id.textview_rate_average)
    TextView rateAverageTV;
    @BindView(R.id.textview_release_time)
    TextView releaseTimeTV;
    @BindView(R.id.imageview_image_poster)
    ImageView imagePosterIV;
    @BindView(R.id.fav_button)
    ImageButton favoriteButton;
    private Cursor mData;
    private int mIdCol, mFavCol;
    private MovieAdapter movieAdapter;
    private boolean favStatus =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.v("hhhh6", "DetailActivity-onCreate");

        MovieListProvider movieListProvider = new MovieListProvider();
        MovieListDBhelper dBhelper = new MovieListDBhelper(this);
        Intent intentThatStartedThisActivity = getIntent();
        Log.v("hhhh6", "intentThatStartedThisActivity    " + intentThatStartedThisActivity.toString());
        ButterKnife.bind(this);
        Log.v("hhhh6", "889");
        myDb = dBhelper.getWritableDatabase();
        Log.v("hhhh6", "890");

        final MovieSelected movie;
        Log.v("hhhh6", "891");
        //currentMovie = bundle.getParcelable(MOVIE_EXTRA);

        if (intentThatStartedThisActivity != null && !getIntent().getBooleanExtra("isNewItem", false)) {

            movie = intentThatStartedThisActivity.getExtras().getParcelable("PARCEL_MOVIE");
            Log.v("hhhh6", "movie   check point");
            //Checking to see if the movie is null:
            Log.v("hhhh6entering", "movie   " + movie.getTitle());
            currentMovie = movie;
            Log.v("hhhh6", "DetailActivity 67");
            String result = currentMovie.getFavMovie() + "";
            Log.v("hhhh6", "DetailActivity 68");

            mUri = getIntent().getData();
            Log.v("hhhh6", "DetailActivity 69");
            //  if (mUri == null) throw new NullPointerException("URI for DetailActivity cannot be null")

            // movieListProvider.query(mUri,null,null,null,null);
            Log.v("hhhh6", "DetailActivity 70");


            //boolean favoredBool = Boolean.valueOf(MovieListContract.MoviesEntry.MOVIE_FAVORED);
            ////at this line the fav button ger the status:

            boolean favoredBool = !Boolean.valueOf(getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_FAVORED).toString());
            movie.setFavMovie(favoredBool);
            Log.v("hhhh6_id", "movie   " + currentMovie.getId());
            Log.v("hhhh6_fav", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_FAVORED));
            Log.v("hhhh6_title", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_TITLE));
            Log.v("hhhh6_id-current", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_ID));


            //favoriteButton.setSelected(favoredBool);
            Log.v("hhhh6_id", "movie   " + movie.getId());
            // final ContentValues values = new ContentValues();
            //  boolean favoredBool = (boolean) values.get(MovieListContract.MoviesEntry.MOVIE_FAVORED);
            // favoriteButton.setSelected(favoredBool);


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
        ////////////////////////////  fav button  ///////////////////
        favoriteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.v("hhhh7_id", "movie   " + currentMovie.getId());
                Log.v("hhhh7_fav", "movie   " + currentMovie.getFavMovie());
                Log.v("hhhh7_fav_db", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_FAVORED));
                Log.v("hhhh7_title", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_TITLE));
                Log.v("hhhh7_id-current", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_ID));

                boolean favoredMovie = !Boolean.valueOf(getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_FAVORED).toString());
                Log.v("hhhh7_fav_bool", "movie   " + favoredMovie);

               // addNewFavorite(currentMovie, favoredMovie);
                favStatus = favoredMovie;
                //   getContentResolver().query(mUri,null,null,null,MovieListContract.MoviesEntry.MOVIE_FAVORED ).get(MovieListContract.MoviesEntry.MOVIE_ID);;
                // Uri singleUri = ContentUris.withAppendedId(UserDictionary.Words.CONTENT_URI,4);

                Log.v("hhhh7_fav_db", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_FAVORED));
                //  nextWord();
                favoriteButton.setSelected(favoredMovie);
                /*if(currentMovie.getFavMovie()==false) {
                    currentMovie.setFavMovie(true);
                    favoriteButton.setSelected(true);
                    addNewFavorite(currentMovie, true);
                    //Toast.makeText(DetailActivity.this, "setFavMovie(true)", Toast.LENGTH_SHORT).show();

                }else {
                    currentMovie.setFavMovie(false);
                    favoriteButton.setSelected(false);
                   // removeAFavorite(needs work);
                    // Toast.makeText(DetailActivity.this, "setFavMovie(false)", Toast.LENGTH_SHORT).show();
                }*/


                Toast.makeText(DetailActivity.this, favoredMovie + "", Toast.LENGTH_SHORT).show();



             /*   if (currentMovie == null){ Log.v("rrrrrrrrrrrr", "currentMovie == null"); return;}

                boolean favored = !currentMovie.getFavMovie();


                ContentValues contentValues = new ContentValues();

                contentValues.put(MovieListContract.MoviesEntry.MOVIE_ID, currentMovie.getId());
                contentValues.put(MovieListContract.MoviesEntry.MOVIE_FAVORED , favored);

                Uri uri = getContentResolver().insert(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, contentValues);

                if(uri != null) {
                    Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
                }*/
            }
        });


    }

    private long addNewFavorite(MovieSelected movieSelected, boolean favBoolean) {
        ContentValues cv = new ContentValues();
        Log.v("hhhh9", "addNewFavorite   " + favBoolean);
        String changeFavoSQL= "";
        // cv.put(MovieListContract.MoviesEntry.MOVIE_NAME, movieSelected.getOriginalTitle());
        // cv.put(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME,  movieSelected.getTableName);
        cv.put(MovieListContract.MoviesEntry.MOVIE_ID, movieSelected.getId());
        cv.put(MovieListContract.MoviesEntry.MOVIE_TITLE, movieSelected.getTitle());
        cv.put(MovieListContract.MoviesEntry.MOVIE_OVERVIEW, movieSelected.getOverview());
        cv.put(MovieListContract.MoviesEntry.MOVIE_VOTE_AVERAGE, movieSelected.getVoteAverage());
        cv.put(MovieListContract.MoviesEntry.MOVIE_VOTE_COUNT, movieSelected.getVoteCount());
        cv.put(MovieListContract.MoviesEntry.MOVIE_BACKDROP_PATH, movieSelected.getBackdrop());
        cv.put(MovieListContract.MoviesEntry.MOVIE_POSTER_PATH, movieSelected.getPosterPath());
        cv.put(MovieListContract.MoviesEntry.MOVIE_RELEASE_DATE, movieSelected.getReleaseDate());
        cv.put(MovieListContract.MoviesEntry.MOVIE_FAVORED, favBoolean);      //  cv.put(MovieListContract.MoviesEntry.MOVIE_FAVORED, movieSelected.getFavMovie());
        Log.v("hhhh9_fav_db", "addNewFavorite   " + getMovieValues(movieSelected).get(MovieListContract.MoviesEntry.MOVIE_FAVORED));
        Log.v("hhhh9_fav_db", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_FAVORED));
        Log.v("hhhh9_title", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_TITLE));
        Log.v("hhhh9_id-current", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_ID));
        //cv.put(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, partySize);
        Uri uri = getContentResolver().insert(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, cv);
        Log.v("hhhh9_fav_db2", "addNewFavorite   " + getMovieValues(movieSelected).get(MovieListContract.MoviesEntry.MOVIE_FAVORED));
        if (uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

        // return getContentResolver().insert(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, cv);

        myDb.update(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME,cv, "where MOVIE_ID ="+ movieSelected.getId(), null);
        return myDb.insert(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME, null, cv);

    }

    private boolean removeAFavorite(long id) {

        return myDb.delete(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME, MovieListContract.MoviesEntry.MOVIE_ID + "=" + id, null) > 0;
    }

    public static ContentValues getMovieValues(MovieSelected movie) {
        final ContentValues values = new ContentValues();
        //   values.put(MovieListContract.MoviesEntry.MOVIE_NAME, movie.getOriginalTitle());
        values.put(MovieListContract.MoviesEntry.MOVIE_ID, movie.getId());
        values.put(MovieListContract.MoviesEntry.MOVIE_TITLE, movie.getTitle());
        values.put(MovieListContract.MoviesEntry.MOVIE_OVERVIEW, movie.getOverview());
        values.put(MovieListContract.MoviesEntry.MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieListContract.MoviesEntry.MOVIE_VOTE_COUNT, movie.getVoteCount());
        values.put(MovieListContract.MoviesEntry.MOVIE_POSTER_PATH, movie.getPosterPath());
        values.put(MovieListContract.MoviesEntry.MOVIE_BACKDROP_PATH, movie.getBackdrop());
        values.put(MovieListContract.MoviesEntry.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieListContract.MoviesEntry.MOVIE_FAVORED, movie.getFavMovie());
        // FavFetchTask favFetchTask = new FavFetchTask();
        // getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_ID);

        return values;
    }

    public void onClickFavorite() {
        if (currentMovie == null) {
            Log.v("rrrrrrrrrrrr", "currentMovie == null");
            return;
        }

        boolean favored = !currentMovie.getFavMovie();


        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieListContract.MoviesEntry.MOVIE_ID, currentMovie.getId());
        contentValues.put(MovieListContract.MoviesEntry.MOVIE_FAVORED, favored);

        Uri uri = getContentResolver().insert(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, contentValues);

        if (uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }


        finish();

    }



 /*   public void favoredMovieTrue(MovieSelected movie, Boolean favored) {
        setMovieTrueFavored(movie.getId(), favored);
    }*/
/*    public void setMovieTrueFavored(Long movieId, Boolean favored) {
        if (currentMovie.getId() != movieId){

            return;
        }
        currentMovie.setFavMovie(favored);
        favoriteButton.setSelected(favored);
    }*/

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
            //  Log.v("yyy48", mData.getString(mFavCol));


            // Set the initial state
            addUpdateNewFavorite(currentMovie, favStatus, hasMovieCheckDB(cursor));
            nextWord();
        }

        public void nextWord() {
            if (mData != null) {
                try {
                    int counter=0;
                    while (mData.moveToNext()) {
                         counter++;

                        /*if (!mData.moveToNext()) {
                            mData.moveToFirst();
                            Log.v("yyy37", mData.getString(mFavCol));
                        }*/

                        if (Integer.parseInt(mData.getString(mIdCol)) == currentMovie.getId()) {
                            favoriteButton.setSelected((mFavCol != 0));
                        } /*else {
                            if (!mData.moveToNext()) {

                                Log.v("yyy20", mData.getString(mIdCol));
                                Log.v("yyy21", mData.getString(mFavCol));
                                return;
                            }*/
                       // }
                        Log.v("yyy38", counter+"");
                        Log.v("yyy38", mData.getString(0));
                        Log.v("yyy38", mData.getString(1));
                        Log.v("yyy38", mData.getString(2));
                        Log.v("yyy38", mData.getString(3));
                        Log.v("yyy38", mData.getString(4));
                        Log.v("yyy38", mData.getString(5));
                        Log.v("yyy38", mData.getString(6));
                        Log.v("yyy38", mData.getString(7));
                        Log.v("yyy38", mData.getString(8));
                        Log.v("yyy38", mData.getString(9));

                        Log.v("yyy39", mData.getString(mIdCol));
                        Log.v("yyy391", Boolean.valueOf(mData.getString(mFavCol)) + "");

                       // favoriteButton.setSelected((mFavCol != 0));
                        // Toast.makeText(DetailActivity.this, Boolean.valueOf(mData.getString(mFavCol)) +"", Toast.LENGTH_SHORT).show();

                    }


                } finally {
                    mData.close();
                }
            }

        }
        private boolean hasMovieCheckDB(Cursor cursor){
            String selectString = "SELECT * FROM " + MovieListContract.MoviesEntry.MOVIE_TABLE_NAME + " WHERE " + MovieListContract.MoviesEntry.MOVIE_ID + " =?";
            boolean hasObject = false;
            if(cursor.moveToFirst()){
                hasObject = true;

                int count = 0;
                while(cursor.moveToNext()){
                    count++;
                }

                Log.d(TAG, String.format("%d records found", count));



            }

            cursor.close();

            return hasObject;
        }

        private long addUpdateNewFavorite(MovieSelected movieSelected, boolean favBoolean,boolean movieExist) {
            ContentValues cv = new ContentValues();
            Log.v("hhhh9", "addNewFavorite   " + favBoolean);
            String changeFavoSQL= "";
            // cv.put(MovieListContract.MoviesEntry.MOVIE_NAME, movieSelected.getOriginalTitle());
            // cv.put(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME,  movieSelected.getTableName);
            cv.put(MovieListContract.MoviesEntry.MOVIE_ID, movieSelected.getId());
            cv.put(MovieListContract.MoviesEntry.MOVIE_TITLE, movieSelected.getTitle());
            cv.put(MovieListContract.MoviesEntry.MOVIE_OVERVIEW, movieSelected.getOverview());
            cv.put(MovieListContract.MoviesEntry.MOVIE_VOTE_AVERAGE, movieSelected.getVoteAverage());
            cv.put(MovieListContract.MoviesEntry.MOVIE_VOTE_COUNT, movieSelected.getVoteCount());
            cv.put(MovieListContract.MoviesEntry.MOVIE_BACKDROP_PATH, movieSelected.getBackdrop());
            cv.put(MovieListContract.MoviesEntry.MOVIE_POSTER_PATH, movieSelected.getPosterPath());
            cv.put(MovieListContract.MoviesEntry.MOVIE_RELEASE_DATE, movieSelected.getReleaseDate());
            cv.put(MovieListContract.MoviesEntry.MOVIE_FAVORED, favBoolean);      //  cv.put(MovieListContract.MoviesEntry.MOVIE_FAVORED, movieSelected.getFavMovie());
            Log.v("hhhh9_fav_db", "addNewFavorite   " + getMovieValues(movieSelected).get(MovieListContract.MoviesEntry.MOVIE_FAVORED));
            Log.v("hhhh9_fav_db", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_FAVORED));
            Log.v("hhhh9_title", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_TITLE));
            Log.v("hhhh9_id-current", "movie   " + getMovieValues(currentMovie).get(MovieListContract.MoviesEntry.MOVIE_ID));
            //cv.put(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, partySize);
            Uri uri = getContentResolver().insert(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, cv);
            Log.v("hhhh9_fav_db2", "addNewFavorite   " + getMovieValues(movieSelected).get(MovieListContract.MoviesEntry.MOVIE_FAVORED));
            if (uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }

            // return getContentResolver().insert(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, cv);
            Long result;
            if (movieExist) {

                result=  Long.parseLong(String.valueOf(myDb.update(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME, cv, "where MOVIE_ID =" + movieSelected.getId(), null)));
            }else {
                result= myDb.insert(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME, null, cv);
            }
            return result;

        }
    }
}