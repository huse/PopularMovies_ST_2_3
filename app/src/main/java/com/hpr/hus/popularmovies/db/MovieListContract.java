package com.hpr.hus.popularmovies.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hk640d on 10/29/2017.
 */

public class MovieListContract {
     public static final String AUTHORITY = "com.hpr.hus.popularmovies";
    public static final Uri BASEURI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIE = "movies";

    public static class MoviesEntry{

        public static final String MOVIE_NAME = "movie";
        public static final String MOVIE_TABLE_NAME = "movies";
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_TITLE = "movie_title";
        public static final String MOVIE_OVERVIEW = "movie_overview";
        public static final String MOVIE_VOTE_COUNT = "movie_vote_count";
        public static final String MOVIE_VOTE_AVERAGE = "movie_vote_average";
        public static final String MOVIE_RELEASE_DATE = "movie_release_date";
        public static final String MOVIE_FAVORED = "movie_favored";
        public static final String MOVIE_POSTER_PATH = "movie_poster_path";
        public static final String MOVIE_BACKDROP_PATH = "movie_backdrop_path";
        public static final Uri MOVIE_CONTENT_URI = BASEURI.buildUpon().appendPath(PATH_MOVIE).build();

        static Uri buildUri(String movieId) {
            return MOVIE_CONTENT_URI.buildUpon().appendPath(movieId).build();
        }
    }

    public static final class MoivieEntry implements BaseColumns {

        public static final String TABLE_NAME = "movies";

    }
    }
}
