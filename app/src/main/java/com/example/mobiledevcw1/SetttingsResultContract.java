package com.example.mobiledevcw1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// contract for recieving the results from changing settings from Settings Activity.
public class SetttingsResultContract extends ActivityResultContract<TransferInfo,TransferInfo> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, TransferInfo info) {
        Intent intent = new Intent(context,Settings.class);
        intent.putExtra("playbackSpeed",info._playbackSpeed);
        intent.putExtra("bgColour",info._bgColour.ordinal());
        return intent;
    }

    @Override
    public TransferInfo parseResult(int i, @Nullable Intent intent) {
        TransferInfo info = new TransferInfo(BgColour.WHITE,1.0F);
        if(i == Activity.RESULT_OK && intent != null) {
            info._bgColour = BgColour.values()[intent.getIntExtra("bgColour",0)];
            info._playbackSpeed = intent.getFloatExtra("playbackSpeed",1.0F);
        }
        return info;
    }
}
