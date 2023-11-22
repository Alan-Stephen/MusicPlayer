package com.example.mobiledevcw1;

// Class to contain info that will be passed around as intents through activities.
public class TransferInfo {
    BgColour _bgColour;
    float _playbackSpeed;

    TransferInfo(BgColour bgColour, float playbackSpeed) {
       _bgColour = bgColour;
       _playbackSpeed = playbackSpeed;
    }
}
