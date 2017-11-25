package com.hpr.hus.popularmovies;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.hpr.hus.popularmovies.db.MovieListContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hk640d on 11/21/2017.
 */

public class AsyncTaskFetchFavorite  extends AsyncTask<String, Void, MovieSelected[]> {

    private Cursor cursor;
    private final TaskInterfaceCompleted mListener;
    private int   cursorLength;
    private int mIdCol,mTitleCol,mOverviewCol,mVoteAverageCol,mVoteCountCol,mBackDropPathCol,mPosterPathCol,mReleaseDateCol,mFavoredCol,mOriginalTitleCol;

    long mId;
    String mTitle;
    String mOverview;
    double mVoteAverage;
    long mVoteCount ;
    String mBackdrop;
    String mPosterPath;
    String mReleaseDate;
    Boolean mIsFavMovie;
    String mOriginalTitle;
    private final String apiKey;
    public AsyncTaskFetchFavorite(TaskInterfaceCompleted listener, Cursor cursor, String apiKey) {
        super();
        Log.v("hhhh3", "AsyncTaskFetchFavorite"); this.apiKey = apiKey;
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
        mOriginalTitleCol = cursor.getColumnIndex(MovieListContract.MoviesEntry.MOVIE_ORIGINAL_TITLE);
    }


    private MovieSelected[] getMovieDataFromDB (Cursor mData){
            MovieSelected[] movies = new MovieSelected[cursorLength];

        for (int i = 0; i < cursorLength; i++) {

            movies[i] = new MovieSelected();
            iterateCursor(cursor);
            Log.v("pppp51", cursor + "");
            Log.v("pppp52", "cursorLength  " + cursor.getCount());
            movies[i].setPosterPath(mPosterPath);
            movies[i].setOverview(mOverview);
            movies[i].setVoteAverage(mVoteAverage);
            movies[i].setReleaseDate(mReleaseDate);
            movies[i].setId(mId);
            movies[i].setTitle(mTitle);
            Log.v("SSSSSSSSS", "TITLE:  " + mTitle);
            movies[i].setVoteCount(mVoteCount);
            movies[i].setFavMovie(mIsFavMovie);
            movies[i].setBackdrop(mBackdrop);
            movies[i].setOriginalTitle(mOriginalTitle);
            Log.v("pppp71", movies[i].toString());
            Log.v("pppp72", " movies[i] "+i + "   "+ movies[i].getFavMovie());
            Log.v("pppu73",  "getMovieValues:   "+ getMovieValues(movies[i]));
            Log.v("pppu74",  "getMovieValuesDB :"+ getMovieValuesFromDB());
        }
        return movies;
    }
/*    private URL getApiUrl(String[] parameters) throws MalformedURLException {
        //http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
        final String TMDB_BASE_URL = "http://api.themoviedb.org/3/movie/"+ parameters[0]+"?";
        final String API_KEY_PARAM = "api_key";

        Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()

                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build();
        Log.v("hhhh3", builtUri.toString());
        return new URL(builtUri.toString());
    }*/

    @Override
    protected MovieSelected[] doInBackground(String... params) {
       /* Log.v("ppp00", params.toString());
        String res="";
        for(String s: params){
            res=res +s;
        }        Log.v("ppp00", res);

        {params[0]="top_rated";
            Log.v("ppp00", params.toString());
            String ress="";
            for(String s: params){
                ress=ress +s;
            }        Log.v("ppp00", ress);
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String result="";
            for(String s: params){
                result = result +" - "+ s;
            }
            Log.v("hhhh3", result);
            String moviesJsonStr = null;

            try {
                URL url = getApiUrl(params);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();
                Log.v("hhh3", "StringBuilder");
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                Log.v("hhh3", "BufferedReader");
                String line;
                while ((line = reader.readLine()) != null) {

                    builder.append(line).append("\n");
                    Log.v("hhh3", "append");
                }

                if (builder.length() == 0) {
                    Log.v("hhh3", "returning null 2");
                    return null;
                }

                moviesJsonStr = builder.toString();
            } catch (IOException e) {
                Log.e("hhh3", "Error ", e);
                return null;
            } finally {

                if (urlConnection != null) {
                    urlConnection.disconnect();
                    Log.v("hhh3", "disconected");
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("hhh3", "Error closing stream", e);
                    }
                }
            }

            try {
                AsyncTaskFetchPopularMovies asyncTaskFetchPopularMovies= new AsyncTaskFetchPopularMovies(mListener,apiKey);
                Log.e("hhh3", asyncTaskFetchPopularMovies.getMoviesDataFromJson(moviesJsonStr).toString());
                return asyncTaskFetchPopularMovies.getMoviesDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                Log.e("hhh3", e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }*/
        return getMovieDataFromDB(cursor);
    }
    private String getMovieValues( MovieSelected movies){
        String result=
                        movies.getId()+ " _ " +
                        movies.getTitle()+ " _ " +
                                movies.getOriginalTitle()+ " _ " +
                        movies.getOverview() + " _ " +
                        movies.getVoteAverage()+ " _ " +
                        movies.getVoteCount()+ " _ " +
                        movies.getBackdrop()+ " _ " +
                        movies.getPosterPathForFav() + " _ " +
                        movies.getReleaseDate()+ " _ " +
                        movies.getFavMovie();
        return result;
    }
    private String getMovieValuesFromDB(){
        String result=
                             mId+ " _ " +
                          mTitle+ " _ " +
                          mOriginalTitle+ " _ " +
                          mOverview + " _ " +
                          mVoteAverage  + " _ " +
                          mVoteCount + " _ " +
                          mBackdrop + " _ " +
                          mPosterPath + " _ " +
                          mReleaseDate+ " _ " +
                          mIsFavMovie;
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
                   mData.getString(9) + " _ " +
                   mData.getString(10));
           mId = Long.parseLong(cursor.getString(mIdCol));
           mTitle = cursor.getString(mTitleCol);
           mOverview = cursor.getString(mOverviewCol);
           mVoteAverage = Double.parseDouble(cursor.getString(mVoteAverageCol));
           mVoteCount = Long.parseLong(cursor.getString(mVoteCountCol));
           mBackdrop = cursor.getString(mBackDropPathCol);

           mPosterPath = mData.getString(mPosterPathCol);

           mReleaseDate = cursor.getString(mReleaseDateCol);
           mIsFavMovie = cursor.getString(mFavoredCol).equals("1");
           Log.v("ppp39", mData.getString(mIdCol));
           mOriginalTitle = cursor.getString(mOriginalTitleCol);
       } else{
           mData.moveToFirst();
           Log.v("ppp81", "***********   Moved to First");
       }

    }
    @Override
    protected void onPostExecute(MovieSelected[] movies) {
        super.onPostExecute(movies);

        mListener.onFetchMoviesTaskCompleted(movies);
    }
}
