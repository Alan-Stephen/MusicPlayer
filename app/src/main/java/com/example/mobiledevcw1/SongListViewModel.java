package com.example.mobiledevcw1;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.ArrayList;

public class SongListViewModel extends ViewModel {
    private float _playbackSpeed = 1;
    private BgColour _bgColour = BgColour.WHITE;
    private ArrayList<String> _musicNames;

    // parse sdcard/Music for files ending with .mp3
    public ArrayList<String> getMusicNames() {
        if(_musicNames != null) {
            return _musicNames;
        }
        String path = "sdcard/Music";

        File dir = new File(path);
        File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".mp3"));

        ArrayList<String> musicNames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                musicNames.add(file.getName());
            }
            _musicNames = musicNames;
            return musicNames;
        }
        return musicNames;
    }

    public float getPlaybackSpeed() {
        return _playbackSpeed;
    }

    public void setPlaybackSpeed(float _playbackSpeed) {
        this._playbackSpeed = _playbackSpeed;
    }

    public BgColour getBgColour() {
        return _bgColour;
    }

    public void setBgColour(BgColour _bgColour) {
        this._bgColour = _bgColour;
    }
}
