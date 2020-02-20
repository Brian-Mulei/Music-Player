package com.mulei.music_player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {



    ArrayList<SongInfo> songs;
    Context context;

    SongAdapter(Context context,ArrayList<SongInfo> songs){
        this.context=context;
        this.songs=songs;
 }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.song_row,parent,false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        TextView SongName,Artist;
        Button play;
        public SongHolder(@NonNull View itemView) {
            super(itemView);

            SongName=itemView.findViewById(R.id.SongName);
            Artist=itemView.findViewById(R.id.Artist);
            play=itemView.findViewById(R.id.playbtn);

        }
    }
}
