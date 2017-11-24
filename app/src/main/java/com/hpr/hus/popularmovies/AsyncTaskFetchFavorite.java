package com.hpr.hus.popularmovies;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.hpr.hus.popularmovies.db.MovieListContract;

import org.json.JSONObject;

/**
 * Created by hk640d on 11/21/2017.
 */

public class AsyncTaskFetchFavorite  extends AsyncTask<String, Void, MovieSelected[]> {

    private Cursor cursor;
    private final TaskInterfaceCompleted mListener;
    private int   cursorLength;
    private int mIdCol,mTitleCol,mOverviewCol,mVoteAverageCol,mVoteCountCol,mBackDropPathCol,mPosterPathCol,mReleaseDateCol,mFavoredCol;

    long mId;
    String mTitle;
    String mOverview;
    double mVoteAverage;
    long mVoteCount ;
    String mBackdrop;
    String mPosterPath;
    String mReleaseDate;
    Boolean mIsFavMovie;

    public AsyncTaskFetchFavorite(TaskInterfaceCompleted listener, Cursor cursor) {
        super();
        Log.v("hhhh3", "AsyncTaskFetchFavorite");
        mListener = listener;
        this.cursor = cursor;
        cursorLength=this.cursor.getCount();
        Log.v("pppp41", "cursorLength  " +cursor.getCount());
        mIdCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_ID);
        mTitleCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_TITLE);
        mOverviewCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_OVERVIEW);
        mVoteAverageCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_VOTE_AVERAGE);
        mVoteCountCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_VOTE_COUNT);
        mBackDropPathCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_BACKDROP_PATH);
        mPosterPathCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_POSTER_PATH);
        mReleaseDateCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_RELEASE_DATE);
        mFavoredCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_FAVORED);
    }


    private MovieSelected[] getMovieDataFromDB (Cursor mData){
            MovieSelected[] movies = new MovieSelected[cursorLength];

        for (int i = 0; i < cursorLength; i++) {

            movies[i] = new MovieSelected();
            iterateCursor(cursor);
            Log.v("pppp51", cursor + "");
            Log.v("pppp52", "cursorLength  " + cursor.getCount());

           // movies[i].setOriginalTitle(movieInfo.getString(TAG_ORIGINAL_TITLE));
            movies[i].setPosterPath(mPosterPath);
            movies[i].setOverview(mOverview);
            movies[i].setVoteAverage(mVoteAverage);
            movies[i].setReleaseDate(mReleaseDate);
            /// stage 2
            movies[i].setId(mId);
            movies[i].setTitle(mTitle);
            Log.v("SSSSSSSSS", "TITLE:  " + mTitle);
            movies[i].setVoteCount(mVoteCount);
            movies[i].setFavMovie(mIsFavMovie);
            movies[i].setBackdrop(mBackdrop);
            Log.v("pppp71", movies[i].toString());
            Log.v("pppp72", " movies[i] "+i + "   "+ movies[i].getFavMovie());
            Log.v("pppu73",  "getMovieValues:   "+ getMovieValues(movies[i]));



        }

        return movies;
    }


    @Override
    protected MovieSelected[] doInBackground(String... strings) {
        return getMovieDataFromDB(cursor);
    }
    private String getMovieValues( MovieSelected movies){



        String result=
                        movies.getId()+ " _ " +
                        movies.getTitle()+ " _ " +
                        movies.getOverview() + " _ " +
                        movies.getVoteAverage()+ " _ " +
                        movies.getVoteCount()+ " _ " +
                        movies.getBackdrop()+ " _ " +
                        movies.getPosterPath() + " _ " +
                        movies.getReleaseDate()+ " _ " +
                        movies.getFavMovie();
        return result;
    }
    private void  iterateCursor(Cursor mData    ){
        int counter=0;
      //  while (mData.moveToNext()) {
       if( mData.moveToNext()) {
           counter++;

           Log.v("ppp37_counter:  ", counter + "");
           Log.v("pppu38_iterateCursor", mData.getString(0) + " _ " +
                   mData.getString(1) + " _ " +
                   mData.getString(2) + " _ " +
                   mData.getString(3) + " _ " +
                   mData.getString(4) + " _ " +
                   mData.getString(5) + " _ " +
                   mData.getString(6) + " _ " +
                   mData.getString(7) + " _ " +
                   mData.getString(8) + " _ " +
                   mData.getString(9));
           mId = Long.parseLong(cursor.getString(mIdCol));
           mTitle = cursor.getString(mTitleCol);
           mOverview = cursor.getString(mOverviewCol);
           mVoteAverage = Double.parseDouble(cursor.getString(mVoteAverageCol));
           mVoteCount = Long.parseLong(cursor.getString(mVoteCountCol));
           mBackdrop = cursor.getString(mBackDropPathCol);
           mPosterPath = cursor.getString(mPosterPathCol);
           mReleaseDate = cursor.getString(mReleaseDateCol);
           mIsFavMovie = cursor.getString(mFavoredCol).equals("1");
           Log.v("ppp39", mData.getString(mIdCol));
       } else{
           mData.moveToFirst();
           Log.v("ppp81", "***********   Moved to First");
       }

    }
}
