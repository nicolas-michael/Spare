package com.example.nicolas.spotifyshare;

/**
 * Created by Nicolas on 9/24/2016.
 */
public class UserEnvironment {

    private boolean spotifyInstalled;
    private boolean wifiEnabled;
    private boolean hasWifiDirect;

    public UserEnvironment(boolean spotifyInstalled, boolean wifiEnabled, boolean hasWifiDirect) {
        this.spotifyInstalled = spotifyInstalled;
        this.wifiEnabled = wifiEnabled;
        this.hasWifiDirect = hasWifiDirect;
    }

    public boolean isSpotifyInstalled() {
        return spotifyInstalled;
    }

    public boolean isWifiEnabled() {
        return wifiEnabled;
    }

    public boolean isHasWifiDirect() {
        return hasWifiDirect;
    }
}
