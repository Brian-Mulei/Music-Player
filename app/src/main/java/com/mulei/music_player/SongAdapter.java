package com.mulei.music_player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {



    ArrayList<SongInfo> songs;
    Context context;
    OnitemClickListener onItemClickListener;
    SongAdapter(Context context,ArrayList<SongInfo> songs){
        this.context=context;
        this.songs=songs;
 }

 public interface OnitemClickListener{
        void onItemClick(Button b,View v,SongInfo obj, int position);
    }

    public  void  setOnItemClickListener(OnitemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.song_row,parent,false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongHolder holder, final int position) {

     final SongInfo c=songs.get(position);
    holder.SongName.setText(c.songname);
        holder.Artist.setText(c.artistName);
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(holder.play,v,c,position);
                }
            }
        });

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
            play=itemView.findViewById(R.id.button);

        }
    }
}
