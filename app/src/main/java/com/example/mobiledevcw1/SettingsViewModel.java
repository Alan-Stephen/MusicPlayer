package com.example.mobiledevcw1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Set;

public class SettingsViewModel extends ViewModel {
    float _playbackSpeed;

    SettingsViewModel(){
        super();
        _playbackSpeed  = 1.0F;
    }

    public void setPlaybackSpeed(float playbackSpeed) {
        _playbackSpeed = playbackSpeed;
    }

    public float getPlaybackSpeed() {
        return _playbackSpeed;
    }
}
