package com.example.mobiledevcw1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MusicService extends Service {
    private final String TAG = "COMP3018";
    private static MP3Player player;
    private static final int PLAYER_ID = 1;
    private static final String CHANNEL_ID = "MusicChannel";
    private IBinder binder = new MusicBinder();

    public class MusicBinder extends Binder {
        public void pause() {
            player.pause();
        }

        public void resume() {
            player.play();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"BINDER");
        return binder;
    }

    @Override
    public void onCreate() {
        if(player == null)
            player = new MP3Player();
        super.onCreate();

        createNotificationChannel();
        Log.d(TAG,"CREATE SERVICE");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = " Download Service ";
            String description = " Used for downloading tasks ";
            int importance = NotificationManager. IMPORTANCE_LOW ;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService (NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
        if (player.getState() == MP3Player.MP3PlayerState.PLAYING)
            player.stop();
        super.onStartCommand(intent,flags,startId);
        String path = intent.getStringExtra("path");

        Log.d(TAG,"STARTING SERVICE, CREATING NOTI");
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Playing Music")
                .setContentText( "Playing: " + path)
                .build();
        startForeground(PLAYER_ID, notification);

        player.load(path,1.0F);
        if(player.getState() == MP3Player.MP3PlayerState.ERROR)
            Log.d(TAG,"ERROR LERROR");
        player.play();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        player.stop();
        super.onDestroy();
        Log.d(TAG,"destroying");
    }
}
