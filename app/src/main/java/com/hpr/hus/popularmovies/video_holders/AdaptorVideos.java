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
    private final ArrayList<Videos> videoList;
    private VideoAdaptorListener videoAdapterListener;

    public AdaptorVideos(ArrayList<Videos> videoList, VideoAdaptorListener videoAdapterListener) {
        this.videoList = videoList;
        this.videoAdapterListener = videoAdapterListener;
    }

    @Override public HolderVideos onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_contents, null);
        return new HolderVideos(layoutView);
    }

    @Override public void onBindViewHolder(HolderVideos holder, int position) {
        final Videos video = videoList.get(position);
        holder.setTrailerText(video.getName());
        holder.setOnClickListener(videoAdapterListener, position);
    }

    @Override public int getItemCount() {
        return videoList.size();
    }

    public Videos getVideoFromPosition(int position) {
        return videoList.get(position);
    }

    public interface VideoAdaptorListener {

        void onVideoClicked(int position);
    }
}
