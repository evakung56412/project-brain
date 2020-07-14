package com.man_jou.projectbrain.fragment;

import android.content.Context;
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
import com.man_jou.projectbrain.adapter.UserIdeasAdapter;
import com.man_jou.projectbrain.callback.ApiCallback;
import com.man_jou.projectbrain.callback.DataCallback;
import com.man_jou.projectbrain.form.RemoveIdeaForm;
import com.man_jou.projectbrain.model.Idea;
import com.man_jou.projectbrain.rest.GetTaskJson;
import com.man_jou.projectbrain.rest.PostTaskJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class UserIdeasFragment extends Fragment implements ApiCallback<String>, DataCallback {

    private TextView empIdeaTV;
    private RecyclerView recyclerIdeas;
    private UserIdeasAdapter adapter;

    private String username;
    private ArrayList<Idea> ideaArrayList;
    private int removePosition;

    public UserIdeasFragment getFragment() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        username = getArguments().getString("username");
        return inflater.inflate(R.layout.fragment_user_ideas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        empIdeaTV = view.findViewById(R.id.empIdeaTV);

        ideaArrayList = new ArrayList<>();
        adapter = new UserIdeasAdapter(getActivity(), ideaArrayList, this);

        recyclerIdeas = view.findViewById(R.id.recyclerIdeas);
        recyclerIdeas.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerIdeas.setAdapter(adapter);

        String request = "http://10.0.2.2:8080/brain/" + username + "/ideas";
        new GetTaskJson<>(String.class, getFragment()).execute(request);
    }

    /*
        ApiCallback<String>
    */
    @Override
    public void postResult(ResponseEntity<String> responseEntity) {

        if (responseEntity.getStatusCode() == HttpStatus.OK)
        {
            ideaArrayList.remove(removePosition);

            if (ideaArrayList.isEmpty()) {
                empIdeaTV.setVisibility(View.VISIBLE);
            }

            adapter.notifyDataSetChanged();

            Toast.makeText(getContext(), responseEntity.getBody(), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getContext(), "Delete Failed! There has some mistakse: " + responseEntity.getStatusCode().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getResult(ResponseEntity<String> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK)
        {
            String response = responseEntity.getBody().substring(1, responseEntity.getBody().length()-1);

            if (!response.equals("")) {
                parseResponse(response);
            }
            else {
                empIdeaTV.setVisibility(View.VISIBLE);
            }
        }
        else {
            Log.i("Get User Ideas Failed", responseEntity.getStatusCode().toString());
        }
    }

    /*
        DataCallback
    */
    @Override
    public void deleteItem(int position, Object object) {
        removePosition = position;

        Idea idea = (Idea) object;
        RemoveIdeaForm form = new RemoveIdeaForm();
        form.setTitle(idea.getTitle());
        form.setContext(idea.getContext());
        form.setContent(idea.getContent());

        new PostTaskJson<RemoveIdeaForm, String>(String.class, getFragment()).execute(form);
    }

    @Override
    public void followUser(int position, Object object) {}

    public void parseResponse(String response) {
        response = response.replaceAll("[{}\"]","");

        int arraySize = 3;
        if (response.contains("author")) {
            arraySize = 8;
        }

        String[] pairs = response.split(",");
        for (int i = 0; i < pairs.length; i += arraySize) {
            Idea idea = new Idea();
            for (int j = i; j < i + arraySize; j++) {
                String[] keyValue = pairs[j].split(":");
                if (keyValue[0].equals("title")) {
                    idea.setTitle(keyValue[1]);
                }
                else if (keyValue[0].equals("context")) {
                    idea.setContext(keyValue[1]);
                }
                else if (keyValue[0].equals("content")) {
                    idea.setContent(keyValue[1]);
                }
            }
            ideaArrayList.add(idea);
        }
        adapter.notifyDataSetChanged();
    }
}