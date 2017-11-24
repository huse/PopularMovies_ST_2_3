package com.hpr.hus.popularmovies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by hk640d on 10/29/2017.
 */

public class MovieListDBhelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "movielist.db";


    private static final int DATABASE_VERSION = 8;


    public MovieListDBhelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + MovieListContract.MoviesEntry.MOVIE_TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + MovieListContract.MoviesEntry.MOVIE_ID + " TEXT NOT NULL,"
                + MovieListContract.MoviesEntry.MOVIE_ORIGINAL_TITLE + " TEXT NOT NULL,"
                + MovieListContract.MoviesEntry.MOVIE_TITLE + " TEXT NOT NULL,"
                + MovieListContract.MoviesEntry.MOVIE_OVERVIEW + " TEXT,"
                + MovieListContract.MoviesEntry.MOVIE_VOTE_AVERAGE + " REAL,"
                + MovieListContract.MoviesEntry.MOVIE_VOTE_COUNT + " INTEGER,"
                + MovieListContract.MoviesEntry.MOVIE_BACKDROP_PATH + " TEXT,"
                + MovieListContract.MoviesEntry.MOVIE_POSTER_PATH + " TEXT,"
                + MovieListContract.MoviesEntry.MOVIE_RELEASE_DATE + " TEXT,"
                + MovieListContract.MoviesEntry.MOVIE_FAVORED + " BOOLEAN NOT NULL DEFAULT FALSE);";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieListContract.MoviesEntry.MOVIE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
