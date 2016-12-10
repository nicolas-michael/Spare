package com.example.nicolas.spotifyshare;

import android.content.Context;
import android.support.annotation.NonNull;

import com.spotify.sdk.android.player.PlaybackState;

import java.util.List;
import java.util.Set;

/**
 * Created by Nicolas on 9/24/2016.
 */
public class User {

    private Set<String> profileImagePaths;
    private List<RecentMetadata> recentMetaData;
    private PlaybackState playbackState;
    private UserEnvironment userEnvironment;

    public User(@NonNull Set<String> profileImagePaths, @NonNull List<RecentMetadata> recentMetaData,
                @NonNull UserEnvironment userEnvironment, @NonNull PlaybackState playbackState) {
        this.profileImagePaths = profileImagePaths;
        this.recentMetaData = recentMetaData;
        this.userEnvironment = userEnvironment;
        this.playbackState = playbackState;
    }

    public Set<String> getProfileImagePaths() {
        return profileImagePaths;
    }

    public List<RecentMetadata> getRecentMetaData() {
        return recentMetaData;
    }

    public PlaybackState getPlaybackState() {
        return playbackState;
    }

    public UserEnvironment getUserEnvironment() {
        return userEnvironment;
    }

    public void setUserEnvironment(@NonNull UserEnvironment userEnvironment) {
        this.userEnvironment = userEnvironment;
    }

    public void setPlaybackState(Context context, PlaybackState playbackState) {
        this.playbackState = playbackState;
        SpotemApplication.getInstance(context).saveUser(context);
    }
}
