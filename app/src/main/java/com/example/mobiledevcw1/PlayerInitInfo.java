package com.example.mobiledevcw1;

public class PlayerInitInfo {
    private float _playbackSpeed;
    private BgColour _bgColour;
    private String _songName;

    PlayerInitInfo(float playbackSpeed, BgColour bgColour, String songName){
        _playbackSpeed =playbackSpeed;
        _bgColour = bgColour;
        _songName = songName;
    }

    public float getPlaybackSpeed() {
        return _playbackSpeed;
    }

    public void setPlaybackSpeed(float playbackSpeed) {
        this._playbackSpeed = playbackSpeed;
    }

    public BgColour getBgColour() {
        return _bgColour;
    }

    public void setBgColour(BgColour _bgColour) {
        this._bgColour = _bgColour;
    }

    public String getSongName() {
        return _songName;
    }

    public void setSongName(String songName) {
        this._songName = songName;
    }
}
