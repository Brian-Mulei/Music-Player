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
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
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

        final Handler handler=new Handler();
        recyclerView= findViewById(R.id.recyclerView2);
        seekbar=findViewById(R.id.seekBar4);
        //SongInfo s =new SongInfo("P.I.M.P", "50 CENT");

        songAdapter=new SongAdapter(this,songs);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
      recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(songAdapter);

        songAdapter.setOnItemClickListener(new SongAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(final Button b, View v, final SongInfo obj, int position) {

               Runnable runnable=new Runnable() {
                   @Override
                   public void run() {
                       try {
                           if(b.getText().toString().equals("Stop")){
                               b.setText("PLAY");
                               mediaPlayer.stop();
                               mediaPlayer.reset();
                               mediaPlayer.release();
                               mediaPlayer=null;
                           }else {

                               mediaPlayer = new MediaPlayer();
                               mediaPlayer.setDataSource(obj.getSongUrl());
                               mediaPlayer.prepareAsync();
                               mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                   @Override
                                   public void onPrepared(MediaPlayer mp) {
                                       mp.start();
                                       seekbar.setProgress(0);
                                       seekbar.setMax(mp.getDuration());
                                       Log.d("Prog", "run: " + mediaPlayer.getDuration());


                                   }
                               });
                               b.setText("Stop");
                           }
                       }catch (IOException e){}
                   }
               };
               handler.postDelayed(runnable,1000);
            }
        });



    CheckPermission();
        Thread t = new runThread();
        t.start();
    }

    public class runThread extends Thread  {


        @Override
        public void run() {
            while (true) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Runwa", "run: " + 1);
                if (mediaPlayer != null) {
                    seekbar.post(new Runnable() {
                        @Override
                        public void run() {
                            seekbar.setProgress(mediaPlayer.getCurrentPosition());
                        }
                    });

                    Log.d("Runwa", "run: " + mediaPlayer.getCurrentPosition());
                }
            }
        }

    }

    private void CheckPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
       else{
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
