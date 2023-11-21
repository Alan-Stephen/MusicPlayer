package com.example.mobiledevcw1;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.ArrayList;

public class SongListViewModel extends ViewModel {
    float _playbackSpeed = 1;
    String _backgroundColor;

    ArrayList<String> _musicNames;
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
}
