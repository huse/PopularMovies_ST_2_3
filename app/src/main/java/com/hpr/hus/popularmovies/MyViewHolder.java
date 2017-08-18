package com.hpr.hus.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by HK640D on 8/16/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    //Todo do the rest
    ImageView imageViewIcon;
    public MyViewHolder(View itemView ){
        super(itemView);
        imageViewIcon = (ImageView) itemView.findViewById(R.id.imageview_image_poster);
        //itemView.setOnClickListener(View.OnClickListener);
    }
}
