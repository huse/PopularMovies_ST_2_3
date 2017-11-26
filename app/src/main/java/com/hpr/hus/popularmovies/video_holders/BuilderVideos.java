package com.hpr.hus.popularmovies.video_holders;

/**
 * Created by hk640d on 11/25/2017.
 */

public class BuilderVideos {
    private String key;
    private String name;
    private String site;
    private String id;
    private int size;
    private String type;
    private String iso;



    @SuppressWarnings("javadocmethod")
    public BuilderVideos setKey(String key) {
        this.key = key;
        return this;
    }
    @SuppressWarnings("javadocmethod")
    public BuilderVideos setType(String type) {
        this.type = type;
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
        return new Videos(id, iso, key, name, site, size, type);
    }
}
