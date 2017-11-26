package com.hpr.hus.popularmovies.video_holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hpr.hus.popularmovies.R;

/**
 * Created by hk640d on 11/25/2017.
 */

public class HolderVideos  extends RecyclerView.ViewHolder {
    private final View view;
    private TextView videoTrailerText;

    HolderVideos(View itemView) {
        super(itemView);
        view = itemView;
        videoTrailerText = view.findViewById(R.id.name_videos);
    }

    void setTrailerText(String text) {
        videoTrailerText.setText(text);
    }
    private Toast mToast;

    void setOnClickListener(AdaptorVideos.VideoAdaptorListener mVideoAdaptorListener, final int clickedItemIndex) {
        view.setOnClickListener(v -> mVideoAdaptorListener.onVideoClicked(clickedItemIndex));
        Log.v("jjjj", "setOnClickListener  clickedItemIndex:" +clickedItemIndex );
        /*String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
        Toast.makeText(mVideoAdapterListener, toastMessage+"", Toast.LENGTH_LONG).show();*/

    }
}