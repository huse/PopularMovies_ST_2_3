package com.hpr.hus.popularmovies.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by wall on 11/4/17.
 */

public class MovieListProvider extends ContentProvider {
    private static final int MOVIE = 100;
    private static final int MOVIE_ID = 101;
    private static final String MOVIE_PATH = "movies";
    private static final String MOVIE_ID_PATH = "movies/*";
    private MovieListDBhelper mMovieListDBhelper;
    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
       //uriMatcher.addURI(MovieListContract.AUTHORITY,MovieListContract.);
        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        Context context = getContext();



        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
