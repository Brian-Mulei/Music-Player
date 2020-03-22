package com.mulei.music_player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<SongInfo> songs=new ArrayList<SongInfo>();
    RecyclerView recyclerView;
    SeekBar seekbar;
    SongAdapter songAdapter;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.recyclerView2);
        seekbar=findViewById(R.id.seekBar4);
        //SongInfo s =new SongInfo("P.I.M.P", "50 CENT");

        songAdapter=new SongAdapter(this,songs);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(songAdapter);

    mediaPlayer=new MediaPlayer();

    
    CheckPermission();
    }

    private void CheckPermission() {
        if(Build.VERSION.SDK_INT>=123) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            return;
            }
        }else{
            loadSongs();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode){
           case 123:
               if (grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    loadSongs();
               }else{
                   Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                   CheckPermission();
               }
               break;
           default:
               super.onRequestPermissionsResult(requestCode, permissions, grantResults);

       }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void loadSongs(){
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection=MediaStore.Audio.Media.IS_MUSIC +"!=0";
        Cursor cursor= getContentResolver().query(uri,null,selection,null,null);

        if(cursor!=null){
            if(cursor.moveToFirst()){
                String name= cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String artist= cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String url= cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
          SongInfo s=new SongInfo(name,artist,url);
          songs.add(s);

            }while (cursor.moveToNext());
        }
        cursor.close();
        songAdapter=new SongAdapter(this,songs);
    }
}
