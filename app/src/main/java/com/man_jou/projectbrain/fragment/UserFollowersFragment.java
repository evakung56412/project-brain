package com.man_jou.projectbrain.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.man_jou.projectbrain.R;
import com.man_jou.projectbrain.adapter.UserFollowersAdapter;
import com.man_jou.projectbrain.callback.ApiCallback;
import com.man_jou.projectbrain.model.Brain;
import com.man_jou.projectbrain.rest.GetTaskJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class UserFollowersFragment extends Fragment implements ApiCallback<String> {

    private TextView totalFollowerTV, empFollowerTV;
    private RecyclerView recyclerFollowers;
    private UserFollowersAdapter adapter;

    private String username;
    private ArrayList<String> followerArrayList;

    public UserFollowersFragment getFragment() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        username = getArguments().getString("username");
        return inflater.inflate(R.layout.fragment_user_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        totalFollowerTV = view.findViewById(R.id.totalFollowerTV);
        empFollowerTV = view.findViewById(R.id.empFollowerTV);

        followerArrayList = new ArrayList<>();
        adapter = new UserFollowersAdapter(getActivity(), followerArrayList);

        recyclerFollowers = view.findViewById(R.id.recyclerFollowers);
        recyclerFollowers.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerFollowers.setAdapter(adapter);

        String request = "http://10.0.2.2:8080/brain/" + username + "/followers";
        new GetTaskJson<>(String.class, getFragment()).execute(request);
    }

    /*
        ApiCallback<String>
    */
    @Override
    public void postResult(ResponseEntity<String> responseEntity) {}

    @Override
    public void getResult(ResponseEntity<String> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK)
        {
            String response = responseEntity.getBody().substring(1, responseEntity.getBody().length()-1);

            if (!response.equals("")) {
                parseResponse(response);
            }
            else {;
                empFollowerTV.setVisibility(View.VISIBLE);
                totalFollowerTV.setText("Total Followers: " + followerArrayList.size());
            }
        }
        else {
            Log.i("Get Followers Failed", responseEntity.getStatusCode().toString());
        }
    }

    public void parseResponse(String response) {
        response = response.replaceAll("[{}\"]","");
        String[] pairs = response.split(",");

        for (int i = 0; i < pairs.length; i ++) {

            String[] keyValue = pairs[i].split(":");

            if (keyValue[0].equals("username")) {
                followerArrayList.add(keyValue[1]);
            }
        }

        adapter.notifyDataSetChanged();
        totalFollowerTV.setText("Total Followers: " + followerArrayList.size());
    }
}