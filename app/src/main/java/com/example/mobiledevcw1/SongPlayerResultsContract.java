package com.example.mobiledevcw1;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SongPlayerResultsContract extends ActivityResultContract<PlayerInitInfo, TransferInfo> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, PlayerInitInfo o) {
        Intent intent = new Intent(context,SongPlayer.class);
        intent.putExtra("playbackSpeed",o.getPlaybackSpeed());
        intent.putExtra("bgColour",o.getBgColour().ordinal());
        intent.putExtra("music",o.getSongName());

        return intent;
    };

    @Override
    public TransferInfo parseResult(int i, @Nullable Intent intent) {
        float playbackSpeed = intent.getFloatExtra("playbackSpeed",1.0F);
        BgColour bgColour= BgColour.values()[intent.getIntExtra("bgColour",1)];

        return new TransferInfo(bgColour,playbackSpeed);
    }
}
