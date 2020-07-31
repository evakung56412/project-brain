package com.man_jou.projectbrain.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.man_jou.projectbrain.R;
import com.man_jou.projectbrain.activity.MainActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private String username;

    private ProfileFragment getFragment() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        username = getArguments().getString("username");
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button ideaBtn = view.findViewById(R.id.ideaBtn);
        ideaBtn.setOnClickListener(this);

        Button todoBtn = view.findViewById(R.id.todoBtn);
        todoBtn.setOnClickListener(this);

        Button followerBtn = view.findViewById(R.id.followerBtn);
        followerBtn.setOnClickListener(this);

        Button updateProfileBtn = view.findViewById(R.id.updateProfileBtn);
        updateProfileBtn.setOnClickListener(this);

        Button signOutBtn = view.findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(this);
    }

    /*
        View.OnClickListener
    */
    @Override
    public void onClick(View view) {
        int id = view.getId();

        Bundle bundle = new Bundle();
        bundle.putString("username", username);

        switch (id) {
            case R.id.ideaBtn:

                UserIdeasFragment userIdeasFragment = new UserIdeasFragment();
                userIdeasFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, userIdeasFragment)
                        .addToBackStack(userIdeasFragment.getClass().getName()).commit();
                break;
            case R.id.todoBtn:

                UserTodosFragment userTodosFragment = new UserTodosFragment();
                userTodosFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, userTodosFragment)
                        .addToBackStack(userTodosFragment.getClass().getName()).commit();
                break;
            case R.id.followerBtn:
                UserFollowersFragment userFollowersFragment = new UserFollowersFragment();
                userFollowersFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, userFollowersFragment)
                        .addToBackStack(userFollowersFragment.getClass().getName()).commit();
                break;
            case R.id.updateProfileBtn:
                UpdateProfileFragment updateProfileFragment = new UpdateProfileFragment();
                updateProfileFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, updateProfileFragment)
                        .addToBackStack(updateProfileFragment.getClass().getName()).commit();
                break;
            case R.id.signOutBtn:
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
                break;
        }
    }
}