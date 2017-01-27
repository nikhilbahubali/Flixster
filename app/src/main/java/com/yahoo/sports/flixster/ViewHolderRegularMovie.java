package com.yahoo.sports.flixster;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nikhilba on 1/26/17.
 */

public class ViewHolderRegularMovie extends RecyclerView.ViewHolder {
    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvOverview;

    public ViewHolderRegularMovie(View itemView) {
        super(itemView);
        ivPoster = (ImageView)itemView.findViewById(R.id.ivMoviePoster);
        tvTitle = (TextView)itemView.findViewById(R.id.tvMovieTitle);
        tvOverview = (TextView)itemView.findViewById(R.id.tvMovieOverview);
    }

    public ImageView getIvPoster() {
        return ivPoster;
    }

    public void setIvPoster(ImageView ivPoster) {
        this.ivPoster = ivPoster;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public TextView getTvOverview() {
        return tvOverview;
    }

    public void setTvOverview(TextView tvOverview) {
        this.tvOverview = tvOverview;
    }
}
