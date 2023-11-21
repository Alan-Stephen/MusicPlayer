package com.example.mobiledevcw1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SongList extends AppCompatActivity {
    private final static String TAG = "COMP3018";
    private SongListViewModel viewModel;

    private ActivityResultLauncher<Void> activityResultLauncher =
            registerForActivityResult(new SetttingsResultContract(), result -> {
                if(result != null) {
                    viewModel._playbackSpeed = result;
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(SongListViewModel.class);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.songsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SongCardRecylclerViewAdapter(this,viewModel.getMusicNames(),viewModel));
        Log.d("COMP3018", viewModel.getMusicNames().toString());
    }

    public void toSettings(View view) {
        activityResultLauncher.launch(null);
    }
}