package com.hpr.hus.popularmovies.video_holders;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hk640d on 11/25/2017.
 */

public class Videos  implements Parcelable {


/*    public static final String YOUTUBEE = "YouTube";
    public static final String TRAILERS = "Trailer";*/
    public static final Parcelable.Creator<Videos> CREATOR = new Parcelable.Creator<Videos>() {
        public Videos createFromParcel(Parcel source) {
            return dataFromParcel(source);
        }

        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };
    private static final String ID = "id";
    private static final String ISO = "iso_639_1";
    private static final String KEY = "key";
    private static final String NAME = "name";
    private static final String SITE = "site";
    private static final String SIZE = "size";
    private static final String TYPE = "type";
    private String id;
    private String iso;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public Videos (String id, String iso, String key, String name, String site, int size, String typee) {
        this.id = id;
        this.iso = iso;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = typee;
    }

    /**
     * Build a {@link Videos} object from a given {@link JSONObject}.
     */
    public static Videos dataFromJson(JSONObject jsonObject) throws JSONException {
        return new BuilderVideos().setId(jsonObject.getString(ID))
                .setIso(jsonObject.getString(ISO))
                .setKey(jsonObject.getString(KEY))
                .setName(jsonObject.getString(NAME))
                .setSite(jsonObject.getString(SITE))
                .setSize(jsonObject.getInt(SIZE))
                .setType(jsonObject.getString(TYPE))
                .createVideo();
    }

    private static Videos dataFromParcel(Parcel in) {
        return new BuilderVideos()
                .setId(in.readString())
                .setIso(in.readString())
                .setKey(in.readString())
                .setName(in.readString())
                .setSite(in.readString())
                .setSize(in.readInt())
                .setType(in.readString())
                .createVideo();
    }

    public String getId() {
        return id;
    }
/*    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
    public String getIso() {
        return iso;
    }
     public String getSite() {
        return site;
    }*/

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }





    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.id);
        dest.writeString(this.iso);
        dest.writeString(this.key);
        dest.writeString(this.name);

        dest.writeString(this.type);
    }
}
