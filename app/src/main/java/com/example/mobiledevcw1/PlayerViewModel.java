package com.example.mobiledevcw1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// handles service related stuff for music service running time.
public class PlayerViewModel extends ViewModel {
    boolean _isPlaying = false;
    MutableLiveData<Integer> _currentPlayback;
    int _totalDuration;
}
