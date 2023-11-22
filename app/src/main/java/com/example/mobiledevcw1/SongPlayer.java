package com.example.mobiledevcw1;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SongPlayer extends AppCompatActivity {
    private SongPlayerViewModel viewModel;
    private MusicService.MusicBinder musicService;

    SongPlayer songPlayer = this;
    ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG,"SERVICE BINDING CONNECTED");
                musicService = (MusicService.MusicBinder) service;

                viewModel.setRunTime(musicService.getCurrentPlayback());

                totalDuration.setText(convertSecondsToMinutes(musicService.getDuration() / 1000));

                // update currentDuration text to runtime of song.
                viewModel.getRunTime().observe(songPlayer, integer -> {
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
    private TextView playbackSpeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent prevIntent = getIntent();
        viewModel = new ViewModelProvider(this).get(SongPlayerViewModel.class);
        viewModel.setBgColour(BgColour.values()[prevIntent.getIntExtra("bgColour",1)]);
        viewModel.setPlaybackSpeed(prevIntent.getFloatExtra("playbackSpeed",1.0F));
        // if song is already playing then don't start service.
        if(!viewModel.getIsPlaying()) {

            Intent intent = new Intent(SongPlayer.this, MusicService.class);
            intent.putExtra("path", "sdcard/Music/" + prevIntent.getStringExtra("music"));
            intent.putExtra("playbackSpeed", viewModel.getPlaybackSpeed());

            startService(intent);
            viewModel.setIsPlaying(true);
        }

        setContentView(R.layout.activity_song_player);

        ((LinearLayout) findViewById(R.id.songPlayerLayout)).setBackgroundColor(Color.parseColor(
                SettingsViewModel.BGColourToString(viewModel.getBgColour())));

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
               stop(null);
            }
        });
        totalDuration = (TextView) findViewById(R.id.totalPlayback2);
        currentDuration = (TextView) findViewById(R.id.currentPlayback);
        playbackSpeed = (TextView) findViewById(R.id.playbackSpeedText);

        playbackSpeed.setText(Float.toString(viewModel.getPlaybackSpeed()));

        // bind service so that we can use it.
        this.bindService(new Intent(this, MusicService.class), serviceConnection,
                Context.BIND_AUTO_CREATE);


    }

    // check if service is connected yet as service connection is async.
    public void resume(View view) {
        if (musicService != null)
            musicService.resume();
    }

    public void pause(View view) {
        if (musicService != null)
            musicService.pause();
    }

    public void stop(View view) {
       Log.d(TAG,"STOPING");
       Intent intent = new Intent();
       Float playbackSpeed = viewModel.getPlaybackSpeed();
       intent.putExtra("bgColour", viewModel.getBgColour().ordinal());
       intent.putExtra("playbackSpeed",playbackSpeed);
       setResult(Activity.RESULT_OK,intent);

       Intent  stopIntent = new Intent(SongPlayer.this,MusicService.class);
       stopService(stopIntent);
        if(serviceConnection!=null) {
            unbindService(serviceConnection);
            serviceConnection = null;
        }
       finish();
    }
}