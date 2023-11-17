package com.example.mobiledevcw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SongList extends AppCompatActivity {
    private final static String TAG = "COMP3018";
    private MusicService.MusicBinder musicService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"SERVICE BINDING CONNECTED");
            musicService = (MusicService.MusicBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"SERVICE BINDING DISCONNECTED");
            musicService = null;
        }
    };
    private ArrayList<String> getMusicNames() {
        String path = "sdcard/Music";

        File dir = new File(path);

        String[] files = dir.list();

        return new ArrayList<>(Arrays.asList(files));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bindService(new Intent(this, MusicService.class), serviceConnection,
                Context.BIND_AUTO_CREATE);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.songsList);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SongCardRecylclerViewAdapter(this,getMusicNames()));
        Log.d("COMP3018", getMusicNames().toString());
    }

    public void start(View view) {
        Intent intent = new Intent(SongList.this, MusicService.class);
        intent.putExtra("path","sdcard/Music/sonic.mp3");
        startService(intent);
        Log.d("COMP3018","START SERVICE");
    }
    public void stop(View view) {

        musicService.pause();
        Log.d("COMP3018","Paused");
    }
    public void resume(View view){
        musicService.resume();
    }


}