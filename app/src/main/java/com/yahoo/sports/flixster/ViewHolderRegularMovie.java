package com.yahoo.sports.flixster;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nikhilba on 1/26/17.
 */

public class ViewHolderRegularMovie extends RecyclerView.ViewHolder {
    @BindView(R.id.ivMoviePoster) ImageView ivPoster;
    @BindView(R.id.tvMovieTitle) TextView tvTitle;
    @BindView(R.id.tvMovieOverview) TextView tvOverview;

    public ViewHolderRegularMovie(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageView getIvPoster() {
        return ivPoster;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvOverview() {
        return tvOverview;
    }
}
