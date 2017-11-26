package com.hpr.hus.popularmovies;


/**
 * Created by hk640d on 8/1/2017.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.hpr.hus.popularmovies.review_holders.Reviews;
import com.hpr.hus.popularmovies.video_holders.Videos;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static android.content.ContentValues.TAG;


class AsyncTaskFetchPopularMovies extends AsyncTask<String, Void, MovieSelected[]> {

    private final String LOG_TAG = AsyncTaskFetchPopularMovies.class.getSimpleName();

    private final String apiKey;
    static String apiKeyReview;
    private final TaskInterfaceCompleted mListener;

    public AsyncTaskFetchPopularMovies(TaskInterfaceCompleted listener, String apiKey) {
        super();
        Log.v("hhhh3", "AsyncTaskFetchPopularMovies");
        mListener = listener;
        this.apiKey = apiKey;
        this.apiKeyReview = apiKey;

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
    public static ArrayList<Reviews> fetchMovieReviewFromJson(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        JSONArray reviews = json.getJSONArray("results");
        ArrayList<Reviews> result = new ArrayList<>();

        for (int i = 0; i < reviews.length(); i++) {
            result.add(Reviews.dataFromJson(reviews.getJSONObject(i)));
        }
        return result;
    }
    public static ArrayList<Videos> fetchMovieVideosFromJson(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        JSONArray videos = json.getJSONArray("results");
        ArrayList<Videos> result = new ArrayList<>();

        for (int i = 0; i < videos.length(); i++) {
            result.add(Videos.fromJson(videos.getJSONObject(i)));
        }
        return result;
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

    public static URL buildReviewVideoUrl(long movieId, String s) {

        String path = String.format("%s/"+s, movieId);
        final String BASE_URL = "http://api.themoviedb.org/3/movie/";
        final String API_KEY_PARAM = "api_key";
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(path)

                .appendQueryParameter(API_KEY_PARAM, apiKeyReview)
                .build();



        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }



    public static String responseFromHttp(URL url) throws IOException {
        Log.v("oooo", "entering getResponseFromHttpUrl");

       // url= new URL("http://api.themoviedb.org/3/movie/238/reviews?api_key=9d26959734af937d3c5cc0893debc5e2");
        //"com.android.okhttp.internal.huc.HttpURLConnectionImpl:"
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Log.v("oooo", " urlConnection "+urlConnection);

        try {
            Log.v("oooo", " InputStream before");
            Log.v("oooo", " urlConnection2   "+urlConnection);
            InputStream in = urlConnection.getInputStream();
            Log.v("oooo", " InputStream "+in);

            Scanner scanner = new Scanner(in);
          //  Log.v("oooo", " scanner "+scanner);

            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }catch (Exception e) {
            Log.v("oooo", "Error ", e);
            return null;
        }
        finally {
            Log.v("oooo", " urlConnection disconnected");
            urlConnection.disconnect();
        }
    }

    public static URL buildVideoUrl(long movieId) {
        final String BASE_URL = "http://api.themoviedb.org/3/movie/";
        final String API_KEY_PARAM = "api_key";

        String path = String.format("%s/videos", movieId);
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(path)

                .appendQueryParameter(API_KEY_PARAM, apiKeyReview)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }
    @Override
    protected void onPostExecute(MovieSelected[] movies) {
        super.onPostExecute(movies);

        mListener.onFetchMoviesTaskCompleted(movies);
    }
}
