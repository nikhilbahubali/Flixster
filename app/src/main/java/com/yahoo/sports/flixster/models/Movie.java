package com.yahoo.sports.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nikhilba on 1/23/17.
 */

public class Movie {
    private final int id;
    private String title;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private double vote_average;
    private String release_date;

    private static final String prefix = "https://image.tmdb.org/t/p/w342";

    public Movie(JSONObject json) throws JSONException {
        id = json.getInt("id");
        title = json.getString("original_title");
        overview = json.getString("overview");
        posterPath = prefix + json.getString("poster_path");
        backdropPath = prefix + json.getString("backdrop_path");
        vote_average = json.getDouble("vote_average");
        release_date = json.getString("release_date");
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

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {
        return id;
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray results) {
        ArrayList<Movie> movies = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {
            try {
                movies.add(new Movie(results.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }

    public static void fromTrailersJSONArray(JSONArray results, ArrayList<String> trailers) {
        for (int i = 0; i < results.length(); i++) {
            try {
                trailers.add(results.getJSONObject(i).getString("source"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
