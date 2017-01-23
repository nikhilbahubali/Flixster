package com.yahoo.sports.flixster;

import android.os.Bundle;
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
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private ListView lvMovieItems;
    private MovieArrayAdapter mMovieArrayAdapter;
    private static final String mUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        populateMovieObjects();
    }

    private void populateMovieObjects() {
        mMovieArrayAdapter = new MovieArrayAdapter(this, mMovies);
        lvMovieItems = (ListView)findViewById(R.id.lvMovieItems);
        lvMovieItems.setAdapter(mMovieArrayAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(mUrl, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    mMovies.addAll(Movie.fromJSONArray(results));
                    mMovieArrayAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
