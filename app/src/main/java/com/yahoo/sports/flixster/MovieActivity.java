package com.yahoo.sports.flixster;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.sports.flixster.adapters.MovieArrayAdapter;
import com.yahoo.sports.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    private static final String mUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private SwipeRefreshLayout swipeContainer;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private ListView lvMovieItems;
    private MovieArrayAdapter mMovieArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        // set up swipe refresh
        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMovies();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        populateMovieObjects();
    }

    private void populateMovieObjects() {
        mMovieArrayAdapter = new MovieArrayAdapter(this, mMovies);
        lvMovieItems = (ListView)findViewById(R.id.lvMovieItems);
        lvMovieItems.setAdapter(mMovieArrayAdapter);

        fetchMovies();
    }

    private void fetchMovies() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(mUrl, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // clear movies list from before
                    mMovieArrayAdapter.clear();
                    mMovies.clear();

                    // load new movies list
                    JSONArray results = response.getJSONArray("results");
                    mMovies.addAll(Movie.fromJSONArray(results));

                    // notify adapter
                    mMovieArrayAdapter.notifyDataSetChanged();

                    // if pull refresh, stop it
                    swipeContainer.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
