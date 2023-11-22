package com.example.mobiledevcw1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SongPlayerViewModel extends ViewModel {
    private BgColour bgColour;
    private float playbackSpeed;

    public BgColour getBgColour() {
        return bgColour;
    }

    public void setBgColour(BgColour bgColour) {
        this.bgColour = bgColour;
    }

    public float getPlaybackSpeed() {
        return playbackSpeed;
    }

    public void setPlaybackSpeed(float playbackSpeed) {
        this.playbackSpeed = playbackSpeed;
    }

    private boolean _isPlaying = false;
    // contains the updating running time of the song
    private MutableLiveData<Integer> _runTime;
    private int _totalDuration;

    public boolean getIsPlaying() {
        return _isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this._isPlaying = isPlaying;
    }

    public MutableLiveData<Integer> getRunTime() {
        return _runTime;
    }

    public void setRunTime(MutableLiveData<Integer> runTime) {
        this._runTime = runTime;
    }

    public int getTotalDuration() {
        return _totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this._totalDuration = totalDuration;
    }
}
