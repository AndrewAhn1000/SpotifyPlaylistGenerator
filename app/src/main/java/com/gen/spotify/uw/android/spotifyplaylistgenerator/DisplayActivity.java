package com.gen.spotify.uw.android.spotifyplaylistgenerator;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import com.gen.spotify.uw.android.spotifyplaylistgenerator.Models.Playlist;
import com.gen.spotify.uw.android.spotifyplaylistgenerator.Models.Owner;
import com.gen.spotify.uw.android.spotifyplaylistgenerator.Models.TrackCollection;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class DisplayActivity extends AppCompatActivity {
    Spinner spinner;
    private String accessToken;
    private static final String spotifyUrl = "https://api.spotify.com/v1/";
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    private final ArrayList<Playlist> playlists = new ArrayList<>();
    private String selectedPlaylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        spinner = (Spinner)findViewById(R.id.spinner);
        Intent intent = getIntent();
        accessToken = intent.getExtras().getString("Access Token");
        getPlaylists();

    }

    public void playlistOnClick(View v){
        if(playlists.size() > 0){
            for(int i=0;i<playlists.size();i++){
                Log.d("Playlist contents",playlists.get(i).getPlaylistName());
                if(playlists.get(i).getPlaylistName() == selectedPlaylist){
                    Intent intent = new Intent(DisplayActivity.this, PlaylistActivity.class);
                    intent.putExtra("PLAYLIST", playlists.get(i));
                    startActivity(intent);
                }
            }
        }else{
            Log.d("Empty playlist", "Playlist is empty");
        }
    }

    private void getPlaylists(){
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call c, IOException e) {
                Log.e("Callback Failed", e.toString());
            }

            @Override
            public void onResponse(Call c, final Response response) throws IOException {
                String jsonObject = response.body().string();
                try {
                    JSONObject obj = new JSONObject(jsonObject);
                    JSONArray items = obj.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        Playlist pl = new Playlist();
                        JSONObject playlistObj = items.getJSONObject(i);
                        pl.setPlaylistName(playlistObj.getString("name"));
                        pl.setCollaborative(playlistObj.getBoolean("collaborative"));
                        pl.setHref(playlistObj.getString("href"));
                        pl.setID(playlistObj.getString("id"));
                        pl.setIsPublic(playlistObj.getBoolean(("public")));
                        JSONObject owner = playlistObj.getJSONObject("owner");
                        Owner ownerObj = new Owner();
                        ownerObj.setDisplayName(owner.getString("display_name"));
                        ownerObj.setHref(owner.getString("href"));
                        ownerObj.setOwnerId(owner.getString("id"));
                        ownerObj.setOwnerType(owner.getString("type"));
                        ownerObj.setUri(owner.getString("uri"));
                        pl.setOwner(ownerObj);
                        pl.setSnapshotId(playlistObj.getString("snapshot_id"));
                        pl.setUri(playlistObj.getString("uri"));
                        pl.setSpotifyType(playlistObj.getString("type"));
                        JSONObject tracks = playlistObj.getJSONObject("tracks");
                        TrackCollection trackObj = new TrackCollection();
                        trackObj.setHref(tracks.getString("href"));
                        trackObj.setTotal(tracks.getInt("total"));
                        pl.setTracks(trackObj);
                        playlists.add(pl);
                    }
                } catch (JSONException e) {
                    Log.e("JSON Error", e.toString());
                }
                DisplayActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] playlistNames = new String[playlists.size()];
                        for(int i=0; i<playlists.size(); i++){
                            playlistNames[i] = playlists.get(i).getPlaylistName();
                        }
                        if(playlists.size() == 0){
                            Log.e("Playlist info", "Playlists are empty");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(DisplayActivity.this, android.R.layout.simple_spinner_dropdown_item, playlistNames);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                            {
                                selectedPlaylist = parent.getItemAtPosition(position).toString(); //this is your selected item

                            }
                            public void onNothingSelected(AdapterView<?> parent)
                            {
                                Log.e("Empty", "Empty Spinner");
                            }
                        });
                    }
                });
            }
        };
        Map<String, String> params = new HashMap<>();
        params.put("limit", "50");
        get(spotifyUrl+"me/playlists", params, callback);
    }

    public void get(String url, Map<String,String>params, Callback responseCallback) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .addHeader("Authorization","Bearer " + accessToken)
                .build();
        client.newCall(request).enqueue(responseCallback);
    }
}
