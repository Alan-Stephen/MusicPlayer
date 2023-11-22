package com.example.mobiledevcw1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

public class SongList extends AppCompatActivity {
    private final static String TAG = "COMP3018";
    private SongListViewModel viewModel;

    // for launching settings activity.
    private ActivityResultLauncher<TransferInfo> settingsResultLauncher =
            registerForActivityResult(new SetttingsResultContract(), result -> {
                if(result != null) {
                    viewModel.setBgColour(result._bgColour);
                    viewModel.setPlaybackSpeed(result._playbackSpeed);

                    // update bg colour.
                    ((LinearLayout) findViewById(R.id.songsListLayout))
                            .setBackgroundColor(Color.parseColor(SettingsViewModel.BGColourToString(viewModel.getBgColour())));
                }
            });

    private ActivityResultLauncher<PlayerInitInfo> playerResultLauncher =
            registerForActivityResult(new SongPlayerResultsContract(), result -> {
                if (result != null) {
                    viewModel.setBgColour(result._bgColour);
                    viewModel.setPlaybackSpeed(result._playbackSpeed);

                    ((LinearLayout) findViewById(R.id.songsListLayout))
                            .setBackgroundColor(Color.parseColor(SettingsViewModel.BGColourToString(viewModel.getBgColour())));
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(SongListViewModel.class);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.songsList);

        // set up intial bg colour.
        ((LinearLayout) findViewById(R.id.songsListLayout))
                .setBackgroundColor(Color.parseColor(SettingsViewModel.BGColourToString(viewModel.getBgColour())));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SongCardRecylclerViewAdapter(this,
                viewModel.getMusicNames(),viewModel,playerResultLauncher));
        Log.d("COMP3018", viewModel.getMusicNames().toString());

    }

    public void toSettings(View view) {
        //  handle the onClick for "settings" button.
        settingsResultLauncher.launch(new TransferInfo(viewModel.getBgColour(),viewModel.getPlaybackSpeed()));
    }
}