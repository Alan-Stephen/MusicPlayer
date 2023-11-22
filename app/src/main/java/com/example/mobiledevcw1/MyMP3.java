package com.example.mobiledevcw1;

import androidx.lifecycle.MutableLiveData;

import android.os.Handler;

// Wrapper of MP3Player
public class MyMP3 extends MP3Player {
    private String _currentSongToPlay;
    private float _playbackSpeed;
    private Handler _handler;
    private MutableLiveData<Integer> _currentRunningTime;
    MyMP3(float intialPlaybackSpeed) {
        super();
        _handler = new Handler();
        _currentRunningTime = new MutableLiveData<>();
        _currentRunningTime.setValue(0);
        _playbackSpeed = intialPlaybackSpeed;
    }
    public void start(){
        this.load(_currentSongToPlay,_playbackSpeed);
        _currentRunningTime.setValue(0);
        stopRunningTime();
        startRunningTime();
        play();
    }

    @Override
    public void stop() {
        super.stop();
        stopRunningTime();
    }
    public void setSongToPlay(String song) {
        _currentSongToPlay = song;
    }

    @Override
    public void setPlaybackSpeed(float playbackSpeed) {
        this._playbackSpeed = playbackSpeed;
        super.setPlaybackSpeed(playbackSpeed);
    }

    public MutableLiveData<Integer> getRunningTime() {
        return _currentRunningTime;
    }

    public void setRunningTime(int currentPlayBack) {
        _currentRunningTime.setValue(currentPlayBack);
    }

    // updates _currentRunningTime to accurately repersent the runtime of song in milliseconds.
    public void startRunningTime() {
        _handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRunningTime(getProgress());
                _handler.postDelayed(this,1000);
            }
        },1000);
    }

    // stops the handler thread updating _currentRunningTime
    public void stopRunningTime() {
        _handler.removeCallbacksAndMessages(null);
    }
}
