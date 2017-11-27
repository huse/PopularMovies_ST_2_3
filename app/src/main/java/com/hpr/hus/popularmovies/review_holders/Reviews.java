package com.hpr.hus.popularmovies.review_holders;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hk640d on 11/24/2017.
 */

public class Reviews  implements Parcelable {
    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        public Reviews createFromParcel(Parcel source) {
            return dataFromParcel(source);
        }

        public Reviews[] newArray(int size) {

            Log.v("review1", "size newArray" + size);
            return new Reviews[size];
        }
    };
    private String contents;
    private String url;;
    private String id;
    private String authors;
    private static final String CONTENT = "content";
    private static final String URL = "url";
    private static final String ID = "id";
    private static final String AUTHOR = "author";

    ;

    public Reviews(String id, String author, String content, String url) {
        this.id = id;
        this.authors = author;
        this.contents = content;
        this.url = url;
        Log.v("review1", "Reviews url" + url);

    }

    public String getId() {
        return id;
    }

    public String getAuthorsw() {
        Log.v("review1", "Reviews authors" + authors);

        return authors;
    }

    public String getContents() {
        Log.v("review1", "Reviews contents" + contents);

        return contents;
    }




    private static Reviews dataFromParcel(Parcel in) {
        Log.v("review1", "fromParcel");
        return new BuilderReviews()
                .setReviewId(in.readString())
                .setAuthorsname(in.readString())
                .setReviewContent(in.readString())
                .setReviewUrl(in.readString())
                .createReview();
    }
    public static Reviews dataFromJson(JSONObject jsonObject) throws JSONException {
        Log.v("review1", "Reviews dataFromJson" + jsonObject);

        return new BuilderReviews()
                .setReviewId(jsonObject.getString(ID))
                .setAuthorsname(jsonObject.getString(AUTHOR))
                .setReviewContent(jsonObject.getString(CONTENT))
                .setReviewUrl(jsonObject.getString(URL))
                .createReview();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.v("review1", "Reviews writeToParcel flags" + flags);
        dest.writeString(this.url);
        dest.writeString(this.id);
        dest.writeString(this.authors);
        dest.writeString(this.contents);

    }

}
