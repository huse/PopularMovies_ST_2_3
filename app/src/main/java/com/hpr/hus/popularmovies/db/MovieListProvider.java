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
import static com.hpr.hus.popularmovies.db.MovieListContract.MoivieEntry.TABLE_NAME;
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



        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mMovieListDBhelper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // Query for the tasks directory and write a default case
        switch (match) {
            // Query for the tasks directory
            case MOVIE:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

            case MOVIE_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

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
                long id = db.insert(TABLE_NAME,null, contentValues);
                if (id>0){
                    returnUri = ContentUris.withAppendedId(MovieListContract.MoivieEntry.CONTENT_URI, id);

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
                                value.getAsLong(MovieListContract.MoivieEntry.COLUMN_MOVIE_ID);
                        if (movieId==0) {
                            throw new IllegalArgumentException("movie id is not available");
                        }

                        long _id = db.insert(MovieListContract.MoivieEntry.TABLE_NAME, null, value);
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
