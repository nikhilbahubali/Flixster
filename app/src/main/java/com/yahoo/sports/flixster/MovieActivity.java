package com.yahoo.sports.flixster;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yahoo.sports.flixster.adapters.MovieRecyclerAdapter;
import com.yahoo.sports.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {
    private static final String MOVIE_API_URL = "https://api.themoviedb.org/3/movie/now_playing";
    private static final String TRAILER_API_URL = "https://api.themoviedb.org/3/movie/209112/trailers";
    private static final String API_KEY = "api_key";
    private static final String API_KEY_VALUE = "a07e22bc18f5cb106bfe4cc1f83ad8ed";


    private ArrayList<Movie> mMovies = new ArrayList<>();
    private ArrayList<String> mTrailers = new ArrayList<>();
    private MovieRecyclerAdapter movieRecyclerAdapter;

    @BindView(R.id.rvMovieItems) RecyclerView rvMovieItems;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        // refresh movie list on swipe
        swipeContainer.setOnRefreshListener(this::fetchMovies);

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        populateMovieObjects();
    }

    private void populateMovieObjects() {
        movieRecyclerAdapter = new MovieRecyclerAdapter(this, mMovies, mTrailers);
        rvMovieItems.setAdapter(movieRecyclerAdapter);
        rvMovieItems.setLayoutManager(new LinearLayoutManager(this));

        fetchTrailers();
        fetchMovies();
    }

    private void fetchTrailers() {
        // create request
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(TRAILER_API_URL).newBuilder();
        String url = urlBuilder.addQueryParameter(API_KEY, API_KEY_VALUE).build().toString();
        Request request = new Request.Builder().url(url).build();

        // submit request, handle response
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    // get json results array
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray results = obj.getJSONArray("youtube");
                    Movie.fromTrailersJSONArray(results, mTrailers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchMovies() {
        // create request
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(MOVIE_API_URL).newBuilder();
        String url = urlBuilder.addQueryParameter(API_KEY, API_KEY_VALUE).build().toString();
        Request request = new Request.Builder().url(url).build();

        // submit request, handle response
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    // get json results array
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray results = obj.getJSONArray("results");
                    final ArrayList<Movie> movies = Movie.fromJSONArray(results);

                    // update list view on ui thread
                    runOnUiThread(() -> {
                        // clear old data
                        mMovies.clear();

                        // load new movies list
                        mMovies.addAll(movies);

                        // notify adapter
                        movieRecyclerAdapter.notifyDataSetChanged();

                        // if pull refresh, stop it
                        swipeContainer.setRefreshing(false);

                        // fill trailers
                        fillTrailers();
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fillTrailers() {
        for (int i = mTrailers.size() - 1, bound = mTrailers.size(); i < mMovies.size(); i++) {
            mTrailers.add(mTrailers.get(new Random().nextInt(bound)));
        }
    }
}
