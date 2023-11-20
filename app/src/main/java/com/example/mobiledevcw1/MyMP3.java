package com.example.mobiledevcw1;

import androidx.lifecycle.MutableLiveData;

import android.os.Handler;
public class MyMP3 extends MP3Player {
    private String _currentSongToPlay;
    private float _playbackSpeed;
    Handler _handler;

    private MutableLiveData<Integer> _currentPlayBack;
    MyMP3(float intialPlaybackSpeed) {
        super();
        _handler = new Handler();
        _currentPlayBack = new MutableLiveData<>();
        _currentPlayBack.setValue(0);
        _playbackSpeed = intialPlaybackSpeed;
    }
    public void start(){
        this.load(_currentSongToPlay,_playbackSpeed);
        _currentPlayBack.setValue(0);
        stopPlaybackTimer();
        startPlaybackTimer();
    }

    @Override
    public void stop() {
        super.stop();
        stopPlaybackTimer();
    }
    public void setSongToPlay(String song) {
        _currentSongToPlay = song;
    }
    public float getPlaybackSpeed() {
        return _playbackSpeed;
    }

    @Override
    public void setPlaybackSpeed(float playbackSpeed) {
        this._playbackSpeed = playbackSpeed;
        super.setPlaybackSpeed(playbackSpeed);
    }

    public MutableLiveData<Integer> getCurrentPlayBack() {
        return _currentPlayBack;
    }

    public void setCurrentPlayBack(int currentPlayBack) {
        _currentPlayBack.setValue(currentPlayBack);
    }

    public void startPlaybackTimer() {
        _handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCurrentPlayBack(getProgress());
                _handler.postDelayed(this,1000);
            }
        },1000);
    }

    public void stopPlaybackTimer() {
        _handler.removeCallbacksAndMessages(null);
    }
}
