package com.hpr.hus.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.hpr.hus.popularmovies.db.MovieListContract;

/**
 * Created by hk640d on 8/1/2017.
 */


public class MovieSelected implements Parcelable {
    private static final String DATE_FORMAT = "MM-dd-yyyy";
    private String mOriginalTitle;
    private String mPosterPath;
    private String mOverview;
    private Double mVoteAverage;
    private String mReleaseDate;
    ///stage 2
    private long mId;
    private String mTitle;
    private long mVoteCount;
    private boolean mIsFavMovie;
    private String mBackdrop;

    public MovieSelected(Object[] mArrays){




        this.mPosterPath = mArrays[7].toString();
        this.mVoteAverage = Double.parseDouble(mArrays[4].toString());
        this.mReleaseDate = mArrays[8].toString();
        this.mId = Long.parseLong(mArrays[1].toString());
        this.mTitle = mArrays[2].toString();
        this.mVoteCount = Long.parseLong(mArrays[5].toString());
        this.mIsFavMovie = mArrays[9].toString().equals("1");
        this.mBackdrop = mArrays[6].toString();
        this.mOverview = mArrays[3].toString();



    }
    public MovieSelected() {
        Log.v("hhhh5", "MovieSelected");
    }
    public long getId() { return mId;    }
    public String getTitle() {
        return mTitle;
    }
    public long getVoteCount() {
        return mVoteCount;
    }
    public boolean getFavMovie() {
        Log.v("FFFFFFFFFFFF"," getFavMovie   " + mIsFavMovie);

        return mIsFavMovie;
    }
    public String getBackdrop() {
        return mBackdrop;
    }

    public void setId(Long id) {  mId = id;    }
    public void setTitle(String title) { mTitle = title;}
    public void setVoteCount(long voteCount) {mVoteCount = voteCount; }
    public void setFavMovie(boolean favMovie) {

        mIsFavMovie = favMovie;
        Log.v("FFFFFFFFFFFF"," setFavMovie   " + mIsFavMovie);}
    public void setBackdrop(String backdrop) { mBackdrop= backdrop; }




    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public void setOverview(String overview) {
        if(!overview.equals("null")) {
            mOverview = overview;
        }
    }

    public void setVoteAverage(Double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public void setReleaseDate(String releaseDate) {
        if(!releaseDate.equals("null")) {
            mReleaseDate = releaseDate;
        }
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getPosterPath() {
        final String TMDB_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";

        return TMDB_POSTER_BASE_URL + mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public Double getVoteAverage() { return mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getDetailedVoteAverage() {
        return String.valueOf(getVoteAverage()) + "/10";
    }

    public String getDateFormat() {
        return DATE_FORMAT;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mTitle);
        dest.writeString(mOriginalTitle);
        dest.writeString(mOverview);
        dest.writeString(mPosterPath);
        dest.writeValue(mVoteAverage);
        dest.writeValue( mVoteCount);
        dest.writeString(mReleaseDate);
        dest.writeString(mBackdrop);
       // dest.writeValue (mIsFavMovie);
        Log.v("kkkkkkkkkk3", "mIsFavMovie " + mIsFavMovie);

    }

    public MovieSelected(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mOriginalTitle = in.readString();
        mOverview = in.readString();
        mPosterPath = in.readString();
        mVoteAverage = (Double) in.readValue(Double.class.getClassLoader());
        mVoteCount = (long) in.readValue(long.class.getClassLoader());
        mReleaseDate = in.readString();
        mBackdrop = in.readString();
        //mIsFavMovie = in.readByte() != 0;
       // mIsFavMovie = true;
      // mIsFavMovie = getMovieValues(movieSelected).get(MovieListContract.MoviesEntry.MOVIE_FAVORED);
        //getFavMovie();

        Log.v("kkkkkkkkkk", "mIsFavMovie " + mIsFavMovie);

    }

    public static final Parcelable.Creator<MovieSelected> CREATOR = new Parcelable.Creator<MovieSelected>() {
        public MovieSelected createFromParcel(Parcel source) {
            return new MovieSelected(source);
        }

        public MovieSelected[] newArray(int size) {
            return new MovieSelected[size];
        }
    };

    public boolean checkFavMovie() {
        return mIsFavMovie;
    }
}
