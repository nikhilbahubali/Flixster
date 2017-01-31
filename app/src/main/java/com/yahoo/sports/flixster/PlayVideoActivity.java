package com.yahoo.sports.flixster;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nikhilba on 1/27/17.
 */

public class PlayVideoActivity extends YouTubeBaseActivity {

    @BindView(R.id.pvVideoPlayer) YouTubePlayerView mYouTubePlayerView;

    private static final String TRAILER_API_URL = "https://api.themoviedb.org/3/movie/%d/trailers";
    private static final String API_KEY = "api_key";
    private static final String API_KEY_VALUE = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);

        final int movieId = getIntent().getIntExtra("Id", 0);
        mYouTubePlayerView.initialize("AIzaSyCS4GJEFSukl5_ybeCNrIRzcOjcOCEeoB8",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        playTrailer(youTubePlayer);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.d("degug", "video failed");
                    }

                    private void playTrailer(YouTubePlayer youTubePlayer) {
                        // create request
                        OkHttpClient client = new OkHttpClient();
                        String trailerUrl = String.format(TRAILER_API_URL, movieId);
                        HttpUrl.Builder urlBuilder = HttpUrl.parse(trailerUrl).newBuilder();
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
                                    JSONObject json = (JSONObject) results.get(0);
                                    String trailerID = json.getString("source");

                                    // play trailer
                                    runOnUiThread(() -> youTubePlayer.loadVideo(trailerID));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
    }
}
