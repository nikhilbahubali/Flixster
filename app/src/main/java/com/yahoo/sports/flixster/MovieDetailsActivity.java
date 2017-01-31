package com.yahoo.sports.flixster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {
    @BindView(R.id.ivMovieDetailsBackdrop) ImageView ivMovieDetailsBackdrop;
    @BindView(R.id.ivMovieDetailsVideoPlayButton) ImageView getIvMovieDetailsVideoPlayButton;
    @BindView(R.id.tvMovieDetailsTitle) TextView tvMovieDetailsTitle;
    @BindView(R.id.tvMovieDetailsReleaseDate) TextView tvMovieDetailsReleaseDate;
    @BindView(R.id.tvMovieDetailsSynopsis) TextView tvMovieDetailsSynopsis;
    @BindView(R.id.rbMovieDetailsRating) RatingBar rbMovieDetailsRating;
    private int mTrailerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Picasso.with(this)
                .load(intent.getStringExtra("BackdropPath"))
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(R.drawable.loader)
                .into(ivMovieDetailsBackdrop);

        tvMovieDetailsTitle.setText(intent.getStringExtra("Title"));
        tvMovieDetailsReleaseDate.setText("Release Date: " + intent.getStringExtra("ReleaseDate"));
        tvMovieDetailsSynopsis.setText(intent.getStringExtra("Synopsis"));
        rbMovieDetailsRating.setRating((float) intent.getDoubleExtra("Rating", 0));
        mTrailerId = intent.getIntExtra("Id", 0);

        getIvMovieDetailsVideoPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlayVideoActivity.class);
                intent.putExtra("Id", mTrailerId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(intent);
            }
        });

    }
}
