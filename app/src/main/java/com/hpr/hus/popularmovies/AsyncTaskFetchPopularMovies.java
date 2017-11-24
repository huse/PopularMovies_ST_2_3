package com.hpr.hus.popularmovies;


/**
 * Created by hk640d on 8/1/2017.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


class AsyncTaskFetchPopularMovies extends AsyncTask<String, Void, MovieSelected[]> {

    private final String LOG_TAG = AsyncTaskFetchPopularMovies.class.getSimpleName();

    private final String apiKey;

    private final TaskInterfaceCompleted mListener;

    public AsyncTaskFetchPopularMovies(TaskInterfaceCompleted listener, String apiKey) {
        super();
        Log.v("hhhh3", "AsyncTaskFetchPopularMovies");
        mListener = listener;
        this.apiKey = apiKey;

    }

    @Override
    protected MovieSelected[] doInBackground(String... params) {
        Log.v("ppp00", params.toString());
        String res="";
        for(String s: params){
            res=res +s;
        }        Log.v("ppp00", res);
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
            Log.e("hhh3", getMoviesDataFromJson(moviesJsonStr).toString());
            return getMoviesDataFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    public MovieSelected[] getMoviesDataFromJson(String moviesJsonStr) throws JSONException {
        final String TAG_RESULTS = "results";
        final String TAG_ORIGINAL_TITLE = "original_title";
        final String TAG_POSTER_PATH = "poster_path";
        final String TAG_OVERVIEW = "overview";
        final String TAG_VOTE_AVERAGE = "vote_average";
        final String TAG_RELEASE_DATE = "release_date";
        // stage 2
        final String TAG_ID = "id";
        final String TAG_TITLE = "title";
        final String TAG_VOTE_COUNT = "vote_count";
        final String TAG_FAVORITE_MOVIE = "favorite_movie";
        final String TAG_BACK_DROP = "backdrop_path";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray resultsArray = moviesJson.getJSONArray(TAG_RESULTS);

        MovieSelected[] movies = new MovieSelected[resultsArray.length()];
        for (int i = 0; i < resultsArray.length(); i++) {
            movies[i] = new MovieSelected();
            JSONObject movieInfo = resultsArray.getJSONObject(i);
            Log.v("SSSSSSSSS", "movieInfo " + movieInfo);
            movies[i].setOriginalTitle(movieInfo.getString(TAG_ORIGINAL_TITLE));
            movies[i].setPosterPath(movieInfo.getString(TAG_POSTER_PATH));
            movies[i].setOverview(movieInfo.getString(TAG_OVERVIEW));
            movies[i].setVoteAverage(movieInfo.getDouble(TAG_VOTE_AVERAGE));
            movies[i].setReleaseDate(movieInfo.getString(TAG_RELEASE_DATE));
            /// stage 2
            movies[i].setId(movieInfo.getLong(TAG_ID));
            movies[i].setTitle(movieInfo.getString(TAG_TITLE));
            Log.v("SSSSSSSSS", "TAG_TITLE " + movieInfo.getString(TAG_TITLE));
            movies[i].setVoteCount(movieInfo.getLong(TAG_VOTE_COUNT));
            //movies[i].setFavMovie(movies.getFavMovie());
            movies[i].setBackdrop(movieInfo.getString(TAG_BACK_DROP));
            Log.v("SSSSSSSSSddd", " movies[i] "+i + "   "+ movies[i].getFavMovie());
            Log.v("SSSSSSSSS587", " movies[i] "+i + "   "+ getMovieValues(movies[i]));

        }
        return movies;
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
    private URL getApiUrl(String[] parameters) throws MalformedURLException {
        //http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
        final String TMDB_BASE_URL = "http://api.themoviedb.org/3/movie/"+ parameters[0]+"?";
        final String API_KEY_PARAM = "api_key";

        Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()

                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build();
        Log.v("hhhh3", builtUri.toString());
        return new URL(builtUri.toString());
    }

    @Override
    protected void onPostExecute(MovieSelected[] movies) {
        super.onPostExecute(movies);

        mListener.onFetchMoviesTaskCompleted(movies);
    }
}
