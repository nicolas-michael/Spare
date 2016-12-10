package com.example.nicolas.spotifyshare;

/**
 * Created by Nicolas on 9/24/2016.
 */
public class RecentMetadata implements Comparable<RecentMetadata> {

    private String trackId;
    private String artistName;
    private String albumName;
    private String trackName;
    private int trackLengthInSec;
    private long timeSent;
    private String albumImageURL;

    public RecentMetadata(String trackId, String artistName, String albumName, String trackName, int trackLengthInSec, long timeSent, String albumImageURL) {
        this.trackId = trackId;
        this.artistName = artistName;
        this.albumName = albumName;
        this.trackName = trackName;
        this.trackLengthInSec = trackLengthInSec;
        this.timeSent = timeSent;
        this.albumImageURL = albumImageURL;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumTitle() {
        return albumName;
    }

    public String getTrackTitle() {
        return trackName;
    }

    public int getTrackLengthInSec() {
        return trackLengthInSec;
    }

    public long getTimeSent() {
        return timeSent;
    }

    public String getAlbumImageURL() {
        return albumImageURL;
    }

    @Override
    public int compareTo(RecentMetadata recentMetadata) {
        return (int) (this.timeSent - recentMetadata.timeSent);
    }
}
