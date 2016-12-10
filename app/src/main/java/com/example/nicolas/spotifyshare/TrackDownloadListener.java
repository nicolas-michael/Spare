package com.example.nicolas.spotifyshare;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Nicolas on 9/25/2016.
 */
public class TrackDownloadListener implements VolleyListener {

    private static final int MAX_TRACKS = 10;
    public static final String spotifyTrackAPI = "https://api.spotify.com/v1/tracks/";

    private Context context;
    private String trackId;
    private String artistName;
    private String albumName;
    private String trackName;
    private int trackLengthInSec;
    private long timeSent;

    public TrackDownloadListener(Context context, String trackId, String artistName, String albumName, String trackName, int trackLengthInSec, long timeSent) {
        this.context = context;
        this.trackId = trackId.replace("spotify:track:", "");
        this.artistName = artistName;
        this.albumName = albumName;
        this.trackName = trackName;
        this.trackLengthInSec = trackLengthInSec;
        this.timeSent = timeSent;
    }

    @Override
    public void onResponse(String response) {
        String albumImageURL = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            albumImageURL = jsonObject.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url");
        } catch (Exception e) {
            onErrorResponse(new VolleyError(e));
        }
        RecentMetadata recentTrack = new RecentMetadata(trackId, artistName, albumName, trackName, trackLengthInSec, timeSent, albumImageURL);
        List<RecentMetadata> recentTracks = SpotemApplication.getInstance(context).getUser().getRecentMetaData();
        recentTracks.add(0, recentTrack);
        if (recentTracks.size() > MAX_TRACKS) {
            recentTracks.remove(recentTracks.size() - 1);
        }
        SpotemApplication.getInstance(context).saveUser(context);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(this.getClass().toString(), error.toString());
    }

    public String getURL() {
        return spotifyTrackAPI + trackId;
    }
}
