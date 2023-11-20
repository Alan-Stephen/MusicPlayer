package com.example.mobiledevcw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SongPlayer extends AppCompatActivity {
    private PlayerViewModel viewModel;
    private MusicService.MusicBinder musicService;

    SongPlayer songPlayer = this;
    ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG,"SERVICE BINDING CONNECTED");
                musicService = (MusicService.MusicBinder) service;
                viewModel._currentPlayback = musicService.getCurrentPlayback();
                totalDuration.setText(convertSecondsToMinutes(musicService.getDuration() / 1000));
                viewModel._currentPlayback.observe(songPlayer, integer -> {
                    currentDuration.setText(convertSecondsToMinutes(integer / 1000));
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG,"SERVICE BINDING DISCONNECTED");
                musicService = null;
            }
    };

    public static String convertSecondsToMinutes(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    private final static String TAG="COMP3018";
    private TextView totalDuration;
    private TextView currentDuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        if(!viewModel._isPlaying) {
            Intent prevIntent = getIntent();

            Intent intent = new Intent(SongPlayer.this, MusicService.class);
            intent.putExtra("path", "sdcard/Music/" + prevIntent.getStringExtra("music"));
            intent.putExtra("playbackSpeed", prevIntent.getFloatExtra("playbackSpeed", 1.0F));

            startService(intent);
            viewModel._isPlaying = true;
        } else {
           Log.d(TAG,"other");
        }

        setContentView(R.layout.activity_song_player);

        totalDuration = (TextView) findViewById(R.id.totalPlayback2);
        currentDuration = (TextView) findViewById(R.id.currentPlayback);
        this.bindService(new Intent(this, MusicService.class), serviceConnection,
                Context.BIND_AUTO_CREATE);
    }

    public void resume(View view) {
       musicService.resume();
    }
    public void pause(View view) {
       musicService.pause();
    }

    public void stop(View view) {
       Log.d(TAG,"STOPING");
       Intent intent = new Intent(SongPlayer.this,MusicService.class);
       stopService(intent);
        if(serviceConnection!=null) {
            unbindService(serviceConnection);
            serviceConnection = null;
        }
       finish();
    }
}