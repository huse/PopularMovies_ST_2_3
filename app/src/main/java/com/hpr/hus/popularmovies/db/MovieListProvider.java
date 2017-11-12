package com.hpr.hus.popularmovies.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.hpr.hus.popularmovies.db.MovieListContract.MoviesEntry.MOVIE_TABLE_NAME;
/**
 * Created by wall on 11/4/17.
 */

public class MovieListProvider extends ContentProvider {
    private static final int MOVIE = 100;
    private static final int MOVIE_ID = 101;
    private static final String MOVIE_PATH = "movies";

    private static final String MOVIE_ID_PATH = "movies/*";
    private MovieListDBhelper mMovieListDBhelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
       uriMatcher.addURI(MovieListContract.AUTHORITY,MovieListContract.PATH_MOVIE,MOVIE);
        uriMatcher.addURI(MovieListContract.AUTHORITY,MovieListContract.PATH_MOVIE + "/#",MOVIE_ID);
        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mMovieListDBhelper = new MovieListDBhelper(context);
        Log.v("hhhhhhhhhhhonCreate", mMovieListDBhelper + "");

        return true;
    }


    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.v("hhhhhhhhhhhquery", mMovieListDBhelper + "");
        final SQLiteDatabase db = mMovieListDBhelper.getReadableDatabase();
        Log.v("hhhhhhhhhhhquery2", db + "");
        // Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Log.v("hhhhhhhhhhhquery3", match + "");
        Cursor retCursor;
        Log.v("hhhhhhhhhhhquery4", "retCursor");

        // Query for the tasks directory and write a default case
        switch (match) {
            // Query for the tasks directory
            case MOVIE:
                Log.v("hhhhhhhhhhhquery6f", MOVIE + "");
                Log.v("hhhhhhhhhhhquery6T", MOVIE_TABLE_NAME + "");
                Log.v("hhhhhhhhhhhquery6p", projection + "");
                Log.v("hhhhhhhhhhhquery6s", selection + "");
                Log.v("hhhhhhhhhhhquery6sa", selectionArgs + "");
                Log.v("hhhhhhhhhhhquery6so", sortOrder + "");
                retCursor = db.query(MOVIE_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                Log.v("hhhhhhhhhhhquery5", retCursor + "");

            case MOVIE_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};
                retCursor = db.query(MOVIE_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                Log.v("hhhhhhhhhhhquery6", retCursor + "");
                break;
            // Default exception
            default:

                Log.v("hhhhhhhhhhhquery7", "default" + "");
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        Log.v("hhhhhhhhhhhquery8", retCursor + "");
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.v("hhhhhhhhhhhquery9", retCursor + "");
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mMovieListDBhelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case MOVIE:
                long id = db.insert(MOVIE_TABLE_NAME,null, contentValues);
                if (id>0){
                    returnUri = ContentUris.withAppendedId(MovieListContract.MoviesEntry.MOVIE_CONTENT_URI, id);

                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);

                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mMovieListDBhelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {

            case MOVIE:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long movieId =
                                value.getAsLong(MovieListContract.MoviesEntry.MOVIE_ID);
                        if (movieId==0) {
                            throw new IllegalArgumentException("movie id is not available");
                        }

                        long _id = db.insert(MovieListContract.MoviesEntry.MOVIE_TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }
}
