package com.example.nicolas.spotifyshare;

/**
 * Created by Nicolas on 9/18/2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.spotify.sdk.android.player.PlaybackState;

public class SpotifyBroadcastReceiver extends BroadcastReceiver {

    static final class BroadcastTypes {
        static final String SPOTIFY_PACKAGE = "com.spotify.music";
        static final String PLAYBACK_STATE_CHANGED = SPOTIFY_PACKAGE + ".playbackstatechanged";
        static final String METADATA_CHANGED = SPOTIFY_PACKAGE + ".metadatachanged";
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        User user = SpotemApplication.getInstance(context).getUser();
        if (user == null) {
            user = SpotemApplication.getInstance(context).loadUser(context.getApplicationContext());
        }
        long timeSent = intent.getLongExtra("timeSent", 0L);
        String action = intent.getAction();
        if (action.equals(BroadcastTypes.METADATA_CHANGED)) {
            String trackId = intent.getStringExtra("id");
            String artistName = intent.getStringExtra("artist");
            String albumName = intent.getStringExtra("album");
            String trackName = intent.getStringExtra("track");
            int trackLengthInSec = intent.getIntExtra("length", 0);
            if (artistName == null || albumName == null) {
                return;
            }
            SpotemApplication.getInstance(context).updateRecentTracks(context.getApplicationContext(), trackId, artistName, albumName, trackName, trackLengthInSec, timeSent);
        } else if (action.equals(BroadcastTypes.PLAYBACK_STATE_CHANGED)) {
            boolean playing = intent.getBooleanExtra("playing", false);
            int positionInMs = intent.getIntExtra("playbackPosition", 0);
            PlaybackState playbackState = new PlaybackState(playing, false, false, false, positionInMs);
            user.setPlaybackState(context.getApplicationContext(), playbackState);
        }
    }
}