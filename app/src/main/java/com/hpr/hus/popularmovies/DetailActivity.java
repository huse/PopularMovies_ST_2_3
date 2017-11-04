package com.hpr.hus.popularmovies;

/**
 * Created by hk640d on 8/1/2017.
 */

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hpr.hus.popularmovies.db.MovieListContract;
import com.hpr.hus.popularmovies.db.MovieListDBhelper;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {


    private SQLiteDatabase myDb;
    private MovieSelected currentMovie;

    @BindView(R.id.textview_original_title) TextView originalTitleTV;
    @BindView(R.id.textview_overview) TextView overViewTV;
    @BindView(R.id.textview_rate_average) TextView rateAverageTV;
    @BindView(R.id.textview_release_time) TextView releaseTimeTV;
    @BindView(R.id.imageview_image_poster) ImageView imagePosterIV;
    @BindView(R.id.fav_button) ImageButton favoriteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.v("hhhh6", "DetailActivity-onCreate");


        MovieListDBhelper dBhelper = new MovieListDBhelper(this);
        Intent intentThatStartedThisActivity = getIntent();
        Log.v("hhhh6", "intentThatStartedThisActivity    "+intentThatStartedThisActivity.toString());
        ButterKnife.bind(this);
        myDb = dBhelper.getWritableDatabase();

        MovieSelected movie;
        //currentMovie = bundle.getParcelable(MOVIE_EXTRA);

        if (intentThatStartedThisActivity != null   && !getIntent().getBooleanExtra("isNewItem", false)) {

                movie = intentThatStartedThisActivity.getExtras().getParcelable("PARCEL_MOVIE");
                Log.v("hhhh6", "movie   check point");
                //Checking to see if the movie is null:
                Log.v("hhhh6entering", "movie   " + movie.toString());
            currentMovie=movie;
            String result=currentMovie.getFavMovie()+"";
           // favoriteButton.setText(result);\
            favoriteButton.setSelected(currentMovie.getFavMovie());
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
                    //Toast.makeText(DetailActivity.this, "setFavMovie(true)", Toast.LENGTH_SHORT).show();

                }else {
                    currentMovie.setFavMovie(false);
                    favoriteButton.setSelected(false);
                    // Toast.makeText(DetailActivity.this, "setFavMovie(false)", Toast.LENGTH_SHORT).show();
                }
                String result=currentMovie.getFavMovie()+"";

                Toast.makeText(DetailActivity.this, currentMovie.getFavMovie()+"", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private long addNewFavorite(String name, int partySize) {
        ContentValues cv = new ContentValues();
        cv.put(MovieListContract.MoviesEntry.MOVIE_NAME, name);
        cv.put(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME, partySize);
        cv.put(MovieListContract.MoviesEntry.MOVIE_ID, partySize);
        cv.put(MovieListContract.MoviesEntry.MOVIE_TITLE, partySize);
        cv.put(MovieListContract.MoviesEntry.MOVIE_OVERVIEW, partySize);
        cv.put(MovieListContract.MoviesEntry.MOVIE_VOTE_COUNT, partySize);
        cv.put(MovieListContract.MoviesEntry.MOVIE_VOTE_COUNT, partySize);
        cv.put(MovieListContract.MoviesEntry.MOVIE_VOTE_AVERAGE, partySize);
        cv.put(MovieListContract.MoviesEntry.MOVIE_RELEASE_DATE, partySize);
        cv.put(MovieListContract.MoviesEntry.MOVIE_FAVORED, partySize);
        cv.put(MovieListContract.MoviesEntry.MOVIE_POSTER_PATH, partySize);
        cv.put(MovieListContract.MoviesEntry.MOVIE_BACKDROP_PATH, partySize);
        //cv.put(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, partySize);

        return myDb.insert(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME, null, cv);
    }
    private boolean removeAFavorite(long id) {
        // COMPLETED (2) Inside, call mDb.delete to pass in the TABLE_NAME and the condition that WaitlistEntry._ID equals id
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
