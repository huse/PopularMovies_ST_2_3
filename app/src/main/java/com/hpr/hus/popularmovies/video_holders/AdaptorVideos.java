package com.hpr.hus.popularmovies.video_holders;

import android.support.v7.widget.RecyclerView;
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

    public AdaptorVideos(ArrayList<Videos> videoList, VideoAdaptorListener videoAdaptorListener) {
        this.videoAdaptorListener = videoAdaptorListener;

        this.videoLists = videoList;
    }

    @Override public HolderVideos onCreateViewHolder(ViewGroup parent, int vType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_contents, null);
        return new HolderVideos(layoutView);
    }

    @Override public void onBindViewHolder(HolderVideos holder, int location) {
        final Videos video = videoLists.get(location);
        holder.setTrailerText(video.getName());
        holder.setOnClickedListener(videoAdaptorListener, location);
    }

    @Override public int getItemCount() {
        return videoLists.size();
    }

    public Videos getVideoFromLocation(int location) {
        return videoLists.get(location);
    }

    public interface VideoAdaptorListener {

        void onVideoClicked(int position);
    }
}
