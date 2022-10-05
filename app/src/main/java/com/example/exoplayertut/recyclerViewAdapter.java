package com.example.exoplayertut;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder> {

    private ArrayList<data> mVideoUrl;
    private Context mContext;

    recyclerViewAdapter(Context context, ArrayList<data> videoUrl){
        this.mVideoUrl = videoUrl;
        this.mContext = context;
    }

    @NonNull
    @Override
    public recyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.recycler_view_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapter.ViewHolder holder, int position) {
        data currentData = mVideoUrl.get(position);
        holder.bindTo(currentData);
    }

    @Override
    public int getItemCount() {
        return mVideoUrl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ExoPlayer exoPlayer;
        private StyledPlayerView exoPlayerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            exoPlayerView = itemView.findViewById(R.id.exoPlayerView);
            exoPlayer = new ExoPlayer.Builder(mContext).build();
            exoPlayerView.setPlayer(exoPlayer);
        }

        public void bindTo(data currentData) {
            Uri uri = Uri.parse(currentData.getVideoUrl());
            MediaItem mediaItem = MediaItem.fromUri(uri);
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();
        }
    }
}
