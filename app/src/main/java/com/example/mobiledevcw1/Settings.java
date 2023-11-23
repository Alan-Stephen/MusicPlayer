package com.example.mobiledevcw1;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


public class Settings extends AppCompatActivity {
    EditText _playbackSpeedInput;
    SettingsViewModel viewModel;

    private static final String TAG="COMP3018";

    private MusicService.MusicBinder _musicService;
    private boolean _isServiceBound;

    ServiceConnection _serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "SERVICE BINDING CONNECTED");
            _musicService = (MusicService.MusicBinder) service;
            _isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "SERVICE BINDING DISCONNECTED");
            _musicService = null;
            _isServiceBound = false;
        }
    };

    // check if music service is running
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

        _playbackSpeedInput = (EditText) findViewById(R.id.playbackSpeed);

        if(viewModel.isJustOpened()) {
            viewModel.setPlaybackSpeed(getIntent().getFloatExtra("playbackSpeed", 1.0F));
            viewModel.setBgColour((BgColour.values()[getIntent().getIntExtra("bgColour", 2)]));
            viewModel.setJustOpened(false);
        }
        // set up intial values of playbackSpeed and background colour
        _playbackSpeedInput.setText(Float.toString(viewModel.getPlaybackSpeed()));
        ((LinearLayout) findViewById(R.id.settingsLayout))
                .setBackgroundColor(Color.parseColor(viewModel.BGColourToString(viewModel.getBgColour())));

        // if service is running bind to service.
        if(isServiceRunning()) {
            this.bindService(new Intent(this, MusicService.class), _serviceConnection,
                    Context.BIND_AUTO_CREATE);
        }

        // add Callback for backbutton so data is perserved.
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
               setResults();
               finish();
            }
        });
        // update viewModel variable with text watcher
        _playbackSpeedInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                float value = 1.0F;
                try {
                    value = Float.parseFloat(_playbackSpeedInput.getText().toString());
                } catch (NumberFormatException ignored){
                }

                viewModel.setPlaybackSpeed(value);
            }
        });
    }

    // sets results for songs lists activity to recieve.
    public void setResults() {
        Intent intent = new Intent();
        Float playbackSpeed = viewModel._playbackSpeed;
        intent.putExtra("bgColour", viewModel.getBgColour().ordinal());
        intent.putExtra("playbackSpeed",playbackSpeed);
        setResult(Activity.RESULT_OK,intent);


        // set playback speed dynamically.
        if(isServiceRunning() && _musicService != null) {
            _musicService.setPlayback(viewModel.getPlaybackSpeed());
        }

        if(_isServiceBound) {
            unbindService(_serviceConnection);
            _serviceConnection = null;
        }
    }

    // apply selected settings and go back
    public void done(View view) {
       setResults();
        finish();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    // Buttons for changing background colour and setting view model value.
    public void changeToRed(View view){
        ((LinearLayout) findViewById(R.id.settingsLayout))
                .setBackgroundColor(Color.parseColor(viewModel.BGColourToString(BgColour.RED)));
        viewModel.setBgColour(BgColour.RED);
    }
    public void changeToBlue(View view){
        ((LinearLayout) findViewById(R.id.settingsLayout))
                .setBackgroundColor(Color.parseColor(viewModel.BGColourToString(BgColour.BLUE)));
        viewModel.setBgColour(BgColour.BLUE);
    }
    public void changeToWhite(View view){
        ((LinearLayout) findViewById(R.id.settingsLayout))
                .setBackgroundColor(Color.parseColor(viewModel.BGColourToString(BgColour.WHITE)));
        viewModel.setBgColour(BgColour.WHITE);
    }

}