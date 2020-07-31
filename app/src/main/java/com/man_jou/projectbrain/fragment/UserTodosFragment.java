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

import com.man_jou.projectbrain.R;
import com.man_jou.projectbrain.adapter.UserTodosAdapter;
import com.man_jou.projectbrain.callback.ApiCallback;
import com.man_jou.projectbrain.callback.IdeaCallback;
import com.man_jou.projectbrain.model.Idea;
import com.man_jou.projectbrain.rest.GetTaskJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;

public class UserTodosFragment extends Fragment implements ApiCallback<String>, IdeaCallback {

    private TextView empTodoTV;
    private RecyclerView recyclerTodos;
    private UserTodosAdapter adapter;

    private String username;
    private ArrayList<Idea> ideaArrayList;

    public UserTodosFragment getFragment() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username = getArguments().getString("username");
        return inflater.inflate(R.layout.fragment_user_todos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        empTodoTV = view.findViewById(R.id.empTodoTV);

        ideaArrayList = new ArrayList<>();
        adapter = new UserTodosAdapter(getActivity(), ideaArrayList, this);

        recyclerTodos = view.findViewById(R.id.recyclerTodos);
        recyclerTodos.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerTodos.setAdapter(adapter);

        String request = "http://10.0.2.2:8080/brain/" + username + "/todos";
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
                empTodoTV.setVisibility(View.GONE);
            }
            else {
                empTodoTV.setVisibility(View.VISIBLE);
            }
        }
        else {
            Log.i("Get User Todos Failed", responseEntity.getStatusCode().toString());
        }
    }

    public void parseResponse(String response) {
        response = response.replaceAll("[{}\"]", "");

        int ideaDataLength = 10;

        String[] pairs = response.split(",");
        for (int i = 0; i < pairs.length; i += ideaDataLength) {
            Idea idea = new Idea();

            for (int j = i; j < i + ideaDataLength; j++) {
                String[] keyValue = pairs[j].split(":");
                if (keyValue[0].equals("id")) {
                    idea.setId(Long.valueOf(keyValue[1]));
                }
                else if (keyValue[0].equals("citeId")) {
                    idea.setCiteId(keyValue[1]);
                }
                else if (keyValue[0].equals("title")) {
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

        Collections.sort(ideaArrayList, Collections.reverseOrder());
        adapter.notifyDataSetChanged();
    }

    /*
        ApiCallback<String>
    */
    @Override
    public void deleteItem(int position, Object object) {
        //TODO: delete todo
    }

    @Override
    public void directToOriginal(Object object) {
        Idea idea = (Idea) object;

        Bundle bundle = new Bundle();
        bundle.putString("citeId", idea.getCiteId());

        OriginalIdeaFragment fragment = new OriginalIdeaFragment();
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                .addToBackStack(fragment.getClass().getName()).commit();
    }
}