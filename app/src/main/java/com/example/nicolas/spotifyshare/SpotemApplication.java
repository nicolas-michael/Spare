package com.example.nicolas.spotifyshare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.spotify.sdk.android.player.PlaybackState;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nicolas on 9/24/2016.
 */
public class SpotemApplication {

    private static final String USER_PREFERENCES = "UserPreferences";

    private static SpotemApplication spotemApplication = new SpotemApplication();
    private User user;
    private Context context;
    private List<User> connectedUsers = new LinkedList<>();

    public static SpotemApplication getInstance(Context context) {
        spotemApplication.setContext(context);
        return context == null ? null : spotemApplication;
    }

    public List<User> getConnectedUsers() {
        return connectedUsers;
    }

    public User getUser() {
        return user;
    }

    public void saveUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.commit();
        sendBroadcast();
    }

    private void sendBroadcast() {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("user_data_changed"));
    }

    public User loadUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_PREFERENCES, context.MODE_PRIVATE);
        String jUser = prefs.getString("user", null);
        if (jUser == null) {
            user = new User(new HashSet<String>(),
                            new LinkedList<RecentMetadata>(),
                            new UserEnvironment(false, false, false),
                            new PlaybackState(false, false, false, false, 0));
        }else {
            Gson gson = new Gson();
            user = gson.fromJson(jUser, User.class);
        }
        return user;
    }

    public UserEnvironment determineUserEnvironment(Context context) {
        return new UserEnvironment(false, false, false);
    }

    public void updateRecentTracks(Context context, String trackId, String artistName, String albumName, String trackName, int trackLengthInSec, long timeSent) {
        TrackDownloadListener trackDownloadListener = new TrackDownloadListener(context, trackId, artistName, albumName, trackName, trackLengthInSec, timeSent);
        NetworkUtils.GetRequest(context, trackDownloadListener.getURL(), trackDownloadListener);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
