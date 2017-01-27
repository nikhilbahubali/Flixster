package com.yahoo.sports.flixster;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by nikhilba on 1/26/17.
 */

public class ViewHolderPopularMovie extends RecyclerView.ViewHolder {
    private ImageView ivBackdrop;

    public ImageView getIvBackdrop() {
        return ivBackdrop;
    }

    public void setIvBackdrop(ImageView ivBackdrop) {
        this.ivBackdrop = ivBackdrop;
    }

    public ViewHolderPopularMovie(View itemView) {
        super(itemView);
        ivBackdrop = (ImageView)itemView.findViewById(R.id.ivMovieBackdrop);
    }
}
