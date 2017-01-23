package com.yahoo.sports.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nikhilba on 1/23/17.
 */

public class Movie {
    private String title;
    private String overview;
    private String posterPath;
    private static final String prefix = "https://image.tmdb.org/t/p/w342/";

    public Movie(JSONObject json) throws JSONException {
        title = json.getString("original_title");
        overview = json.getString("overview");
        posterPath = prefix + json.getString("poster_path");
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray results) {
        ArrayList<Movie> movies = new ArrayList<>();

        for(int i = 0; i < results.length(); i++) {
            try {
                movies.add(new Movie(results.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }
}
