package com.yahoo.sports.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.yahoo.sports.flixster.MovieDetailsActivity;
import com.yahoo.sports.flixster.PlayVideoActivity;
import com.yahoo.sports.flixster.R;
import com.yahoo.sports.flixster.ViewHolderPopularMovie;
import com.yahoo.sports.flixster.ViewHolderRegularMovie;
import com.yahoo.sports.flixster.models.Movie;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by nikhilba on 1/26/17.
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Movie> mMovies;
    private ArrayList<String> mTrailers;
    private static int TYPE_REGULAR_MOVIE = 0;
    private static int TYPE_POPULAR_MOVIE = 1;
    private static double POPULAR_MOVIE_MIN_RATING = 6.0;


    public MovieRecyclerAdapter(Context context, ArrayList<Movie> movies, ArrayList<String> trailers) {
        super();
        mContext = context;
        this.mMovies = movies;
        mTrailers = trailers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_REGULAR_MOVIE) {
            viewHolder = new ViewHolderRegularMovie(inflater.inflate(R.layout.movie_item, parent, false));
        } else {
            viewHolder = new ViewHolderPopularMovie(inflater.inflate(R.layout.movie_item_popular, parent, false));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        if (getItemViewType(position) == TYPE_REGULAR_MOVIE) {
            ViewHolderRegularMovie holderRegularMovie = (ViewHolderRegularMovie)holder;
            holderRegularMovie.getTvTitle().setText(movie.getTitle());
            holderRegularMovie.getTvOverview().setText(movie.getOverview());
            holderRegularMovie.getTvTitle().setText(movie.getTitle());
            // load poster image
            holderRegularMovie.getIvPoster().setImageResource(0);
            Picasso.with(mContext)
                    .load(movie.getPosterPath())
                    .transform(new RoundedCornersTransformation(10, 10))
                    .placeholder(R.drawable.loader)
                    .into(holderRegularMovie.getIvPoster());

            holderRegularMovie.getIvPoster().setOnClickListener(v -> {
                Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtra("Title", movie.getTitle());
                intent.putExtra("ReleaseDate", movie.getRelease_date());
                intent.putExtra("Synopsis", movie.getOverview());
                intent.putExtra("Rating", movie.getVote_average());
                intent.putExtra("BackdropPath", movie.getBackdropPath());
                intent.putExtra("Trailer", mTrailers.get(position));
                mContext.startActivity(intent);
            });

        } else {
            ViewHolderPopularMovie holderPopularMovie = (ViewHolderPopularMovie)holder;
            // load backdrop image
            holderPopularMovie.getIvBackdrop().setImageResource(0);
            Picasso.with(mContext)
                    .load(movie.getBackdropPath())
                    .transform(new RoundedCornersTransformation(10, 10))
                    .placeholder(R.drawable.loader)
                    .into(holderPopularMovie.getIvBackdrop());

            holderPopularMovie.getIvVideoPlayButton().setOnClickListener(v -> {
                Intent intent = new Intent(mContext, PlayVideoActivity.class);
                intent.putExtra("Trailer", mTrailers.get(position));
                mContext.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMovies.get(position).getVote_average() > POPULAR_MOVIE_MIN_RATING ? TYPE_POPULAR_MOVIE : TYPE_REGULAR_MOVIE;
    }
}
