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

    public AsyncTaskFetchFavorite(TaskInterfaceCompleted listener, Cursor cursor) {
        super();
        Log.v("hhhh3", "AsyncTaskFetchFavorite");
        mListener = listener;
        this.cursor = cursor;
        cursorLength=this.cursor.getCount();
        Log.v("pppp41", "cursorLength  " +cursor.getCount());

    }


    private MovieSelected[] getMovieDataFromDB (Cursor mData){

        mIdCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_ID);
        mTitleCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_TITLE);
        mOverviewCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_OVERVIEW);
        mVoteAverageCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_VOTE_AVERAGE);
        mVoteCountCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_VOTE_COUNT);
        mBackDropPathCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_BACKDROP_PATH);
        mPosterPathCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_POSTER_PATH);
        mReleaseDateCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_RELEASE_DATE);
        mFavoredCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_FAVORED);




        MovieSelected[] movies = new MovieSelected[cursorLength];

        for (int i = 0; i < cursorLength; i++) {

            movies[i] = new MovieSelected();
            iterateCursor(cursor);
            Log.v("pppp51", cursor + "");
            Log.v("pppp52", "cursorLength  " + cursor.getCount());
            cursor.moveToNext();
            Log.v("pppp53", "mIdCol  " + cursor.getString(1));
            long mId = Long.parseLong(cursor.getString(mIdCol));
            String mTitle = cursor.getString(mTitleCol);
            String mOverview = cursor.getString(mOverviewCol);
            double mVoteAverage = Double.parseDouble(cursor.getString(mVoteAverageCol));
            long mVoteCount = Long.parseLong(cursor.getString(mVoteCountCol));
            String mBackdrop = cursor.getString(mBackDropPathCol);
            String mPosterPath = cursor.getString(mPosterPathCol);
            String mReleaseDate = cursor.getString(mReleaseDateCol);
            Boolean mIsFavMovie = cursor.getString(mFavoredCol).equals("1");




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

            Log.v("SSSSSSSSSddd", " movies[i] "+i + "   "+ movies[i].getFavMovie());

        }

        return movies;
    }



    @Override
    protected MovieSelected[] doInBackground(String... strings) {
        return getMovieDataFromDB(cursor);
    }
    private void  iterateCursor(Cursor mData    ){
        int counter=0;
        while (mData.moveToNext()) {

            counter++;

            Log.v("ppp37_counter", counter +"");
            Log.v("ppp38", mData.getString(0)+ " _ "+ mData.getString(1)+ " _ "+
                    mData.getString(2)+ " _ "+
                    mData.getString(3)+ " _ "+
                    mData.getString(4)+ " _ "+
                    mData.getString(5)+ " _ "+
                    mData.getString(6)+ " _ "+
                    mData.getString(7)+ " _ "+
                    mData.getString(8)+ " _ "+
                    mData.getString(9));
            Log.v("ppp39", mData.getString(mIdCol));

            mData.moveToFirst();

        }
    }
}
