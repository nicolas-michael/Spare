package com.example.nicolas.spotifyshare;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nicolas on 9/25/2016.
 */
public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private List<RecentMetadata> recentMetadatas;

    public TrackAdapter(List<RecentMetadata> recentMetadatas) {
        this.recentMetadatas = recentMetadatas;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track, parent, false);
        return new TrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        RecentMetadata recentMetadata = recentMetadatas.get(position);
        Picasso.with(holder.albumImage.getContext()).load(recentMetadata.getAlbumImageURL()).into(holder.albumImage);
        holder.trackTitle.setText(recentMetadata.getTrackTitle());
        holder.artistName.setText(recentMetadata.getArtistName());
        holder.albumTitle.setText(recentMetadata.getAlbumTitle());
    }

    @Override
    public int getItemCount() {
        return recentMetadatas.size();
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {
        ImageView albumImage;
        TextView trackTitle;
        TextView artistName;
        TextView albumTitle;

        public TrackViewHolder(View v) {
            super(v);
            albumImage = (ImageView) v.findViewById(R.id.album_image);
            trackTitle = (TextView) v.findViewById(R.id.track_title);
            artistName = (TextView) v.findViewById(R.id.artist_name);
            albumTitle = (TextView) v.findViewById(R.id.album_title);
        }
    }
}
