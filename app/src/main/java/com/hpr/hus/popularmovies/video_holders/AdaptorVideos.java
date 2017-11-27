package com.hpr.hus.popularmovies.video_holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hpr.hus.popularmovies.R;

import java.util.ArrayList;

/**
 * Created by hk640d on 11/25/2017.
 */

public class AdaptorVideos extends RecyclerView.Adapter<HolderVideos> {
    private final ArrayList<Videos> videoLists;
    private VideoAdaptorListener videoAdaptorListener;

    public AdaptorVideos(ArrayList<Videos> videoLists, VideoAdaptorListener videoAdaptorListener) {
        Log.v("video1", "AdaptorVideos" + videoLists);

        this.videoLists = videoLists;
        this.videoAdaptorListener = videoAdaptorListener;


    }

    @Override public HolderVideos onCreateViewHolder(ViewGroup parent, int vType) {
        Log.v("video1", "onCreateViewHolder" + vType);

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_contents, null);
        return new HolderVideos(layoutView);
    }

    @Override public void onBindViewHolder(HolderVideos holder, int location) {
        Log.v("video1", "onBindViewHolder" + location);

        final Videos video = videoLists.get(location);
        holder.setTrailerText(video.getName());
        holder.setOnClickedListener(videoAdaptorListener, location);
    }

    @Override public int getItemCount() {
        int itemCount = videoLists.size();
        Log.v("video1", "getItemCount" + itemCount);

        return itemCount;
    }

    public Videos getVideoFromLocation(int location) {
        Log.v("video1", "getVideoFromLocation" + location);

        return videoLists.get(location);
    }

    public interface VideoAdaptorListener {

        void onVideoClicked(int location);
    }
}
