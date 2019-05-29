package com.gen.spotify.uw.android.spotifyplaylistgenerator;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.view.View;
import android.util.Log;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "d1460243a7784b6bb72a4c52db638279";
    private String mAccessToken;
    private static String testString = "hello world";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void buttonOnClick(View v){
        final AuthenticationRequest request = getAuthenticationRequest();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    public void webOnClick(View v){
        final AuthenticationRequest request = getAuthenticationRequest();
        AuthenticationClient.openLoginInBrowser(this, request);
    }

    private AuthenticationRequest getAuthenticationRequest() {
        return new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, getRedirectUri().toString())
                .setScopes(new String[]{"streaming", "playlist-modify-public", "playlist-modify-private",
                "playlist-read-private", "playlist-read-collaborative", })
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("Activity Result", "This was called.");
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            Log.d("Request check", "Request Code == Request Code");
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    mAccessToken = response.getAccessToken();
                    Log.d("Access Token", "Access token grabbed");
                    Intent activity = new Intent(this, DisplayActivity.class);
                    activity.putExtra("Access Token", mAccessToken);
                    activity.putExtra("Test", testString);
                    finish();
                    startActivity(activity);
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Log.e("Error","Could not retrieve access token");

                // Most likely auth flow was cancelled
                default:
                    Log.d("No luck", "Try again");// Handle other cases
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Uri uri = intent.getData();
        Log.d("New  intent", "Intent was called");
        if (uri != null) {
            Log.d("Uri", "Uri was not null");
            AuthenticationResponse response = AuthenticationResponse.fromUri(uri);
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    Log.d("Access Token", "Access token grabbed");
                    mAccessToken = response.getAccessToken();
                    Intent activity = new Intent(this, DisplayActivity.class);
                    activity.putExtra("Access Token", mAccessToken);
                    activity.putExtra("Test", testString);
                    finish();
                    startActivity(activity);
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Log.e("Error","Could not retrieve access token");
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
                    Log.d("No luck", "Try again");
            }
        }
    }

    private Uri getRedirectUri() {
        return new Uri.Builder()
                .scheme(getString(R.string.com_spotify_sdk_redirect_scheme))
                .authority(getString(R.string.com_spotify_sdk_redirect_host))
                .build();
    }

    public String getCurrentAccessToken(){
        return new String(mAccessToken);
    }
}
