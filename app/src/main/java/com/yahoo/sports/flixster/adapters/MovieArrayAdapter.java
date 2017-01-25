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

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.yahoo.sports.flixster.R.id.ivMoviePoster;

/**
 * Created by nikhilba on 1/23/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    private static class ViewHolder {
        public ImageView ivImage;
        public TextView tvTitle;
        public TextView tvOverview;
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
        ViewHolder viewHolder = new ViewHolder();

        // inflate view if required
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);

            // load title
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvMovieTitle);
            // load overview
            viewHolder.tvOverview = (TextView)convertView.findViewById(R.id.tvMovieOverview);
            // reset any previously loaded image
            viewHolder.ivImage = (ImageView) convertView.findViewById(ivMoviePoster);

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
