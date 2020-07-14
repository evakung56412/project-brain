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
import com.man_jou.projectbrain.model.Idea;

import java.util.ArrayList;

public class UserIdeasAdapter extends RecyclerView.Adapter<UserIdeasAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Idea> listOfIdeas;
    private DataCallback callback;

    public UserIdeasAdapter(Context context, ArrayList<Idea> listOfIdeas, DataCallback callback) {
        this.context = context;
        this.listOfIdeas = listOfIdeas;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View oneItem = inflater.inflate(R.layout.view_user_idea_row, parent, false);
        return new ViewHolder(oneItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Idea idea = listOfIdeas.get(position);
        holder.userIdeaTitleTV.setText(idea.getTitle());
        holder.userIdeaContextTV.setText(idea.getContext());
        holder.userIdeaContentTV.setText(idea.getContent());

        holder.deleteIdeaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.deleteItem(position, idea);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfIdeas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView userIdeaTitleTV, userIdeaContextTV, userIdeaContentTV;
        Button deleteIdeaBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userIdeaTitleTV = itemView.findViewById(R.id.userIdeaTitleTV);
            this.userIdeaContextTV = itemView.findViewById(R.id.userIdeaContextTV);
            this.userIdeaContentTV = itemView.findViewById(R.id.userIdeaContentTV);
            this.deleteIdeaBtn = itemView.findViewById(R.id.deleteIdeaBtn);
        }
    }
}
