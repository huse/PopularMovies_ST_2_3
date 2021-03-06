package com.hpr.hus.popularmovies.review_holders;

import android.util.Log;

/**
 * Created by hk640d on 11/24/2017.
 */

public class BuilderReviews  {
    private String contents;
    private String url;
    private String id;
    private String authors;

    @SuppressWarnings("javadocmethod")
    public BuilderReviews setAuthorsname(String author) {
        this.authors = author;
        return this;
    }
    @SuppressWarnings("javadocmethod")
    public BuilderReviews setReviewUrl(String url) {
        this.url = url;
        return this;
    }

    @SuppressWarnings("javadocmethod")
    public BuilderReviews setReviewId(String id) {
        Log.v("review1", "BuilderReviews");

        this.id = id;
        return this;
    }



    @SuppressWarnings("javadocmethod")
    public BuilderReviews setReviewContent(String content) {
        Log.v("review1", "content" +content);

        this.contents = content;
        return this;
    }


    @SuppressWarnings("javadocmethod")
    public Reviews createReview() {
        return new Reviews(id, authors, contents, url);
    }
}