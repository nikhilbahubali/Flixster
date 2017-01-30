package com.yahoo.sports.flixster;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by nikhilba on 1/27/17.
 */

public class PlayVideoActivity extends YouTubeBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.pvVideoPlayer);

        youTubePlayerView.initialize("AIzaSyCS4GJEFSukl5_ybeCNrIRzcOjcOCEeoB8",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.loadVideo("fis-9Zqu2Ro");
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.d("degug", "video failed");
                    }
                });
    }
}
