package com.example.mobiledevcw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Settings extends AppCompatActivity {
    EditText playbackSpeedInput;
    SettingsViewModel viewModel;

    private static final String TAG="COMP3018";

    private MusicService.MusicBinder musicService;
    private boolean isServiceBound;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "SERVICE BINDING CONNECTED");
            musicService = (MusicService.MusicBinder) service;
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "SERVICE BINDING DISCONNECTED");
            musicService = null;
            isServiceBound = false;
        }
    };

    private boolean isServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (MusicService.class.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        playbackSpeedInput = (EditText) findViewById(R.id.playbackSpeed);
        playbackSpeedInput.setText(Float.toString(viewModel._playbackSpeed));

        if(isServiceRunning()) {
            this.bindService(new Intent(this, MusicService.class), serviceConnection,
                    Context.BIND_AUTO_CREATE);
        }
        playbackSpeedInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                float value = 1.0F;
                try {
                    value = Float.parseFloat(playbackSpeedInput.getText().toString());
                } catch (NumberFormatException e){
                }

                viewModel.setPlaybackSpeed(value);
                if(isServiceRunning() && musicService != null) {
                    musicService.setPlayback(viewModel.getPlaybackSpeed());
                }
            }
        });
    }

    public void done(View view) {
        Intent intent = new Intent();
        Float playbackSpeed = Float.parseFloat(playbackSpeedInput.getText().toString());
        intent.putExtra("playbackSpeed",playbackSpeed);
        setResult(Activity.RESULT_OK,intent);

        if(isServiceBound) {
            unbindService(serviceConnection);
            serviceConnection = null;
        }

        finish();
    }
}