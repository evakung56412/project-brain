package com.man_jou.projectbrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.man_jou.projectbrain.R;
import com.man_jou.projectbrain.callback.DataCallback;
import com.man_jou.projectbrain.model.Brain;
import com.man_jou.projectbrain.model.Idea;

import java.util.ArrayList;
import java.util.List;

public class HomeIdeaAdapter extends RecyclerView.Adapter<HomeIdeaAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Idea> listOfIdeas;
    private DataCallback callback;

    public HomeIdeaAdapter(Context context, ArrayList<Idea> listOfIdeas, DataCallback callback) {
        this.context = context;
        this.listOfIdeas = listOfIdeas;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View oneItem = inflater.inflate(R.layout.view_home_row, parent, false);
        return new ViewHolder(oneItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Idea idea = listOfIdeas.get(position);
        holder.ideaTitleTV.setText(idea.getTitle());
        holder.ideaContextTV.setText(idea.getContext());
        holder.ideaContentTV.setText(idea.getContent());
        holder.ideaAuthorTV.setText("Author: " + idea.getAuthor().getUsername());

        holder.followUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.followUser(position, idea);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfIdeas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView ideaTitleTV, ideaContextTV, ideaContentTV, ideaAuthorTV;
        Button followUserBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ideaTitleTV = itemView.findViewById(R.id.ideaTitleTV);
            this.ideaContextTV = itemView.findViewById(R.id.ideaContextTV);
            this.ideaContentTV = itemView.findViewById(R.id.ideaContentTV);
            this.ideaAuthorTV = itemView.findViewById(R.id.ideaAuthorTV);
            this.followUserBtn = itemView.findViewById(R.id.followUserBtn);
        }
    }
}
