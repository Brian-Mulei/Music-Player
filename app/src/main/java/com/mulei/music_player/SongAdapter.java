package com.mulei.music_player;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        public SongHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
