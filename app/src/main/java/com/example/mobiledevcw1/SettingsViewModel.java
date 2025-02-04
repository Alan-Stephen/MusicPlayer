package com.example.mobiledevcw1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.math.BigDecimal;

public class SettingsViewModel extends ViewModel {
    public float _playbackSpeed;

    private BgColour bgColour = BgColour.WHITE;
    private boolean _justOpened = true;

    public boolean isJustOpened() {
        return _justOpened;
    }

    public void setJustOpened(boolean justOpened) {
        this._justOpened = justOpened;
    }

    SettingsViewModel(){
        super();
        _playbackSpeed = 1.0F;
    }

    public void setPlaybackSpeed(float playbackSpeed) {
        _playbackSpeed = playbackSpeed;
    }

    public float getPlaybackSpeed() {
        return _playbackSpeed;
    }

    public BgColour getBgColour() {
        return bgColour;
    }

    public void setBgColour(BgColour bgColour) {
        this.bgColour = bgColour;
    }

    public static String BGColourToString(BgColour colour) {
        switch (colour) {
            case BLUE:
                return "#B2FFFF";
            case RED:
                return "#f08080";
            default:
                return "#ffffff";
        }
    }
}
