package com.example.mobiledevcw1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongCardRecylclerViewAdapter extends RecyclerView.Adapter<SongCardRecylclerViewAdapter.SongViewHolder> {

    private List<String> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public SongCardRecylclerViewAdapter(Context context, List<String> data) {
        this.data = data;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.song_card,parent,false) ;
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.songName);
            Button button = itemView.findViewById(R.id.playMusicButton);

            button.setOnClickListener(v -> {
                Intent intent = new Intent(context,SongPlayer.class);
                intent.putExtra("music",text.getText().toString());

                context.startActivity(intent);
            });
        }

        void bind(final String string){
            text.setText(string);
        }
    }
}
