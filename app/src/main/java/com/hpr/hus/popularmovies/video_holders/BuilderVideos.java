package com.hpr.hus.popularmovies.video_holders;

import android.util.Log;

/**
 * Created by hk640d on 11/25/2017.
 */

public class BuilderVideos {
    private String keys;
    private String name;
    private String site;
    private String id;
    private int size;
    private String typee;
    private String iso;



    @SuppressWarnings("javadocmethod")
    public BuilderVideos setKey(String key) {
        this.keys = key;
        return this;
    }
    @SuppressWarnings("javadocmethod")
    public BuilderVideos setType(String type) {
        this.typee = type;
        return this;
    }
    @SuppressWarnings("javadocmethod")
    public BuilderVideos setId(String id) {
        this.id = id;
        return this;
    }

    @SuppressWarnings("javadocmethod")
    public BuilderVideos setIso(String iso) {
        this.iso = iso;
        return this;
    }



    @SuppressWarnings("javadocmethod")
    public BuilderVideos setName(String name) {
        this.name = name;
        return this;
    }

    @SuppressWarnings("javadocmethod")
    public BuilderVideos setSite(String site) {
        this.site = site;
        return this;
    }

    @SuppressWarnings("javadocmethod")
    public BuilderVideos setSize(int size) {
        this.size = size;
        return this;
    }



    @SuppressWarnings("javadocmethod")
    public Videos createVideo() {
        Log.v("video1", "createVideo: " + id+ iso+ keys+ name+ site+ size +typee);

        return new Videos(id, iso, keys, name, site, size, typee);
    }
}
