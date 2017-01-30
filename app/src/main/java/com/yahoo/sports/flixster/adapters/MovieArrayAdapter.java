package com.yahoo.sports.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.sports.flixster.R;
import com.yahoo.sports.flixster.models.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by nikhilba on 1/23/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    static class ViewHolder {
        @BindView(R.id.ivMoviePoster) ImageView ivImage;
        @BindView(R.id.tvMovieTitle) TextView tvTitle;
        @BindView(R.id.tvMovieOverview) TextView tvOverview;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get movie item for the view position
        Movie movie = getItem(position);

        // viewholder cache
        ViewHolder viewHolder;

        // inflate view if required
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());
        viewHolder.ivImage.setImageResource(0);

        // poster or backdrop based on portrait/landscape
        String imagePath;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            imagePath = movie.getPosterPath();
        } else {
            imagePath = movie.getBackdropPath();
        }

        // load poster image
        Picasso.with(getContext())
                .load(imagePath)
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(R.drawable.loader)
                .into(viewHolder.ivImage);

        return convertView;
    }
}
