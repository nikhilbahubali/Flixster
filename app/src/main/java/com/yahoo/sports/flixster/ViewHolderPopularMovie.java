package com.yahoo.sports.flixster;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nikhilba on 1/26/17.
 */

public class ViewHolderPopularMovie extends RecyclerView.ViewHolder {
    @BindView(R.id.ivMovieBackdrop) ImageView ivBackdrop;

    public ImageView getIvBackdrop() {
        return ivBackdrop;
    }

    public ViewHolderPopularMovie(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
