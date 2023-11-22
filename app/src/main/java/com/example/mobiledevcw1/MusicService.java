package com.example.mobiledevcw1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;

// Service which plays music in foreground
public class MusicService extends Service {
    private final String TAG = "COMP3018";
    private static MyMP3 player;
    private static final int PLAYER_ID = 1;
    private static final String CHANNEL_ID = "MusicChannel";
    private IBinder binder = new MusicBinder();

    // binder to make music service methods accessible
    public class MusicBinder extends Binder {
        public void pause() {
            player.pause();
        }
        public void resume() {
            player.play();
        }
        public void setPlayback(float playbackSpeed) {
            player.setPlaybackSpeed(playbackSpeed);
        }
        MutableLiveData<Integer> getCurrentPlayback() {
            return player.getRunningTime();
        }
        int getDuration() {
            return player.getDuration();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"RETURNING BINDER");
        return binder;
    }

    @Override
    public void onCreate() {
        if(player == null)
            player = new MyMP3(1.0F);
        createNotificationChannel();
        super.onCreate();

        Log.d(TAG,"CREATING MUSIC SERVICE");
    }

    private void createNotificationChannel() {
        CharSequence name = "Music Service ";
        String description = "For playing Music";
        int importance = NotificationManager.IMPORTANCE_HIGH ;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService (NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
        super.onStartCommand(intent,flags,startId);
        if (player.getState() == MP3Player.MP3PlayerState.PLAYING)
            player.stop();

        String path = intent.getStringExtra("path");
        Log.d(TAG,"STARTING TO PLAY MUSIC");
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Playing Music")
                .setContentText( "Playing: " + path)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();
        startForeground(PLAYER_ID, notification);


        Float playbackSpeed = intent.getFloatExtra("playbackSpeed",1.0F);
        player.setSongToPlay(path);
        player.setPlaybackSpeed(playbackSpeed);
        player.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){

        // make sure that the current run time updating loop is stopped.
        player.stop();
        super.onDestroy();
        Log.d(TAG,"STOPPING MUSIC SERVICE");
    }
}
