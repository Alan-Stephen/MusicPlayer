package com.example.mobiledevcw1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter class for creating individual cards for the recycler view.
public class SongCardRecylclerViewAdapter extends RecyclerView.Adapter<SongCardRecylclerViewAdapter.SongViewHolder> {

    private List<String> _data;
    private Context _context;
    private LayoutInflater _layoutInflater;
    private SongListViewModel _viewModel;
    private ActivityResultLauncher<PlayerInitInfo> _launcher;

    // takes in launcher for SongPlayer so that we can bind launcher.launch to button
    public SongCardRecylclerViewAdapter(Context context, List<String> data, SongListViewModel viewModel,
                                        ActivityResultLauncher<PlayerInitInfo> launcher) {
        this._data = data;
        this._context = context;
        this._layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this._launcher = launcher;
        _viewModel = viewModel;
    }
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = _layoutInflater.inflate(R.layout.song_card,parent,false) ;
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.bind(_data.get(position));
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.songName);
            Button button = itemView.findViewById(R.id.playMusicButton);

            button.setOnClickListener(v -> {
                _launcher.launch(new PlayerInitInfo(_viewModel.getPlaybackSpeed(),_viewModel.getBgColour(),text.getText().toString()));
            });
        }

        void bind(final String string){
            text.setText(string);
        }
    }
}
