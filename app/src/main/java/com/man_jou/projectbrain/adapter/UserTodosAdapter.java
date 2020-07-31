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
import com.man_jou.projectbrain.callback.IdeaCallback;
import com.man_jou.projectbrain.model.Idea;

import java.util.ArrayList;

public class UserTodosAdapter extends RecyclerView.Adapter<UserTodosAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Idea> listOfIdeas;
    private IdeaCallback callback;

    public UserTodosAdapter(Context context, ArrayList<Idea> listOfIdeas, IdeaCallback callback) {
        this.context = context;
        this.listOfIdeas = listOfIdeas;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View oneItem = inflater.inflate(R.layout.view_user_todo_row, parent, false);
        return new ViewHolder(oneItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Idea idea = listOfIdeas.get(position);
        holder.userTodoTitleTV.setText(idea.getTitle());
        holder.userTodoContextTV.setText(idea.getContext());
        holder.userTodoContentTV.setText(idea.getContent());

        holder.deleteTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: delete ToDo from Brain
                Toast.makeText(context, "test", Toast.LENGTH_LONG).show();
            }
        });

        holder.userTodoContextTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.directToOriginal(idea);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfIdeas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView userTodoTitleTV, userTodoContextTV, userTodoContentTV;
        Button deleteTodoBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.userTodoTitleTV = itemView.findViewById(R.id.userTodoTitleTV);
            this.userTodoContextTV = itemView.findViewById(R.id.userTodoContextTV);
            this.userTodoContentTV = itemView.findViewById(R.id.userTodoContentTV);
            this.deleteTodoBtn = itemView.findViewById(R.id.deleteTodoBtn);
        }
    }
}
