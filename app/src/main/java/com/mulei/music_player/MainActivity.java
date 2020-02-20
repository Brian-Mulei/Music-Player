package com.mulei.music_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<SongInfo> songs=new ArrayList<SongInfo>();
    RecyclerView recyclerView;
    SeekBar seekbar;
    SongAdapter songAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

recyclerView= findViewById(R.id.recyclerView2);
seekbar=findViewById(R.id.seekBar4);
songAdapter=new SongAdapter(this,songs);

    }
}
