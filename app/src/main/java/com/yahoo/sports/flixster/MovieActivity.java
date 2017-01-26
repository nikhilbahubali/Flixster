package com.yahoo.sports.flixster;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.yahoo.sports.flixster.adapters.MovieArrayAdapter;
import com.yahoo.sports.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {
    private static final String API_URL = "https://api.themoviedb.org/3/movie/now_playing";
    private static final String API_KEY = "api_key";
    private static final String API_KEY_VALUE = "a07e22bc18f5cb106bfe4cc1f83ad8ed";


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
                fetchMovies(false);
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

        fetchMovies(true);
    }

    private void fetchMovies(final boolean firstLoad) {
        // create request
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_URL).newBuilder();
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // clear old data
                            mMovieArrayAdapter.clear();
                            mMovies.clear();

                            // load new movies list
                            mMovies.addAll(movies);

                            // notify adapter
                            mMovieArrayAdapter.notifyDataSetChanged();

                            // if pull refresh, stop it
                            swipeContainer.setRefreshing(false);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
