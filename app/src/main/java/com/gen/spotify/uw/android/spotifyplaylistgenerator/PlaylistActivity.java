package com.gen.spotify.uw.android.spotifyplaylistgenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import com.gen.spotify.uw.android.spotifyplaylistgenerator.Models.Playlist;

public class PlaylistActivity extends AppCompatActivity {
    private Playlist myPlaylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        Intent intent = getIntent();
        myPlaylist = (Playlist) intent.getParcelableExtra("PLAYLIST");
        Log.d("Playlist name", myPlaylist.getPlaylistName());
    }
}
