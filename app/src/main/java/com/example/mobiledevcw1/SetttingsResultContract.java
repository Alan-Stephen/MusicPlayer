package com.example.mobiledevcw1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SetttingsResultContract extends ActivityResultContract<Void,Float> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Void unused) {
        return new Intent(context,Settings.class);
    }

    @Override
    public Float parseResult(int i, @Nullable Intent intent) {
        Float playbackSpeed = null;
        if(i == Activity.RESULT_OK && intent != null) {
            return intent.getFloatExtra("playbackSpeed",1.0F);
        }
        return playbackSpeed;
    }
}
