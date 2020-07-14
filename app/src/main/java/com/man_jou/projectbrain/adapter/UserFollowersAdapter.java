package com.man_jou.projectbrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.man_jou.projectbrain.R;

import java.util.ArrayList;

public class UserFollowersAdapter extends RecyclerView.Adapter<UserFollowersAdapter.ViewHolder>{

    private Context context;
    private ArrayList<String> listOfFollowers;

    public UserFollowersAdapter(Context context, ArrayList<String> listOfFollowers) {
        this.context = context;
        this.listOfFollowers = listOfFollowers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View oneItem = inflater.inflate(R.layout.view_user_follower_row, parent, false);
        return new ViewHolder(oneItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String username = listOfFollowers.get(position);
        holder.followerUserNameTV.setText(username);
    }

    @Override
    public int getItemCount() {
        return listOfFollowers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView followerUserNameTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.followerUserNameTV = itemView.findViewById(R.id.followerUserNameTV);
        }
    }
}
