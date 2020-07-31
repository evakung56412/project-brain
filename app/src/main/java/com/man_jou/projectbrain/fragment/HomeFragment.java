package com.man_jou.projectbrain.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.man_jou.projectbrain.R;
import com.man_jou.projectbrain.adapter.HomeIdeaAdapter;
import com.man_jou.projectbrain.callback.ApiCallback;
import com.man_jou.projectbrain.callback.HomeCallback;
import com.man_jou.projectbrain.callback.IdeaCallback;
import com.man_jou.projectbrain.form.FollowForm;
import com.man_jou.projectbrain.model.Brain;
import com.man_jou.projectbrain.model.Idea;
import com.man_jou.projectbrain.rest.GetTaskJson;
import com.man_jou.projectbrain.rest.PostTaskJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment implements ApiCallback<String>, HomeCallback, TextWatcher, IdeaCallback {

    private TextView empHomeIdeaTV;
    private EditText searchUsernameET, searchTitleET;
    private RecyclerView recyclerHome;
    private HomeIdeaAdapter adapter;

    private ArrayList<Idea> ideaArrayList, searchedIdeaArrayList;
    private String username, followedUserName;

    public HomeFragment getFragment() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        username = getArguments().getString("username");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        empHomeIdeaTV = view.findViewById(R.id.empHomeIdeaTV);
        searchUsernameET = view.findViewById(R.id.searchUsernameET);
        searchTitleET = view.findViewById(R.id.searchTitleET);

        ideaArrayList = new ArrayList<>();
        searchedIdeaArrayList = new ArrayList<>();
        adapter = new HomeIdeaAdapter(getActivity(), searchedIdeaArrayList, this, this);

        recyclerHome = view.findViewById(R.id.recyclerHome);
        recyclerHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerHome.setAdapter(adapter);

        searchUsernameET.addTextChangedListener(this);
        searchTitleET.addTextChangedListener(this);

        String request = "http://10.0.2.2:8080/ideas";
        new GetTaskJson<>(String.class, getFragment()).execute(request);
    }

    /*
        ApiCallback<String>
    */
    @Override
    public void getResult(ResponseEntity<String> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK)
        {
            // remove []
            String response = responseEntity.getBody().substring(1, responseEntity.getBody().length()-1);

            if (!response.equals(""))
            {
                parseResponse(response);
                empHomeIdeaTV.setVisibility(View.GONE);
            }
            else {
                empHomeIdeaTV.setText("No one post ideas.");
                empHomeIdeaTV.setVisibility(View.VISIBLE);
            }
        }
        else {
            Log.i("Get All Ideas Failed: ", responseEntity.getStatusCode().toString());
        }
    }

    @Override
    public void postResult(ResponseEntity<String> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK)
        {
            Toast.makeText(getContext(), "Follow successfully!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getContext(), "Follow Failed! There has some mistakes: " + responseEntity.getStatusCode().toString(), Toast.LENGTH_LONG).show();
        }
    }

    /*
        HomeCallback
    */
    @Override
    public void followUser(Object object) {
        Idea idea = (Idea) object;
        followedUserName = idea.getAuthor().getUsername();

        if (username.equals(followedUserName))
        {
            Toast.makeText(getContext(), "Sorry, cannot follow yourself.", Toast.LENGTH_LONG).show();
        }
        else {
            FollowForm form = new FollowForm();
            form.setFollowerUsername(username);
            form.setFollowedUsername(followedUserName);

            new PostTaskJson<FollowForm, String>(String.class, getFragment()).execute(form);
        }
    }

    @Override
    public void citeIdea(Object object) {
        Idea idea = (Idea) object;
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("citeId", String.valueOf(idea.getId()));
        bundle.putString("title", idea.getTitle());

        CiteIdeaFragment fragment = new CiteIdeaFragment();
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                .addToBackStack(fragment.getClass().getName()).commit();
    }

    /*
        TextWatcher
    */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (!searchUsernameET.getText().toString().equals(""))
        {
            searchTitleET.setEnabled(false);
            String username = searchUsernameET.getText().toString();

            searchedIdeaArrayList.clear();
            for ( Idea idea : ideaArrayList) {
                if (idea.getAuthor().getUsername().contains(username)) {
                    searchedIdeaArrayList.add(idea);
                }
            }

            if (searchedIdeaArrayList.isEmpty())
            {
                empHomeIdeaTV.setText("This user does not exist.");
                empHomeIdeaTV.setVisibility(View.VISIBLE);
            }
            else {
                empHomeIdeaTV.setVisibility(View.GONE);
            }
        }
        else if (!searchTitleET.getText().toString().equals(""))
        {
            searchUsernameET.setEnabled(false);

            String title = searchTitleET.getText().toString();
            searchedIdeaArrayList.clear();
            for (Idea idea : ideaArrayList) {
                if (idea.getTitle().contains(title)) {
                    searchedIdeaArrayList.add(idea);
                }
            }

            if (searchedIdeaArrayList.isEmpty()) {
                empHomeIdeaTV.setText("This title does not exist.");
                empHomeIdeaTV.setVisibility(View.VISIBLE);
            }
            else {
                empHomeIdeaTV.setVisibility(View.GONE);
            }
        }
        else {
            searchUsernameET.setEnabled(true);
            searchTitleET.setEnabled(true);
            empHomeIdeaTV.setVisibility(View.GONE);
            searchedIdeaArrayList.clear();
            searchedIdeaArrayList.addAll(ideaArrayList);
        }

        Collections.reverse(searchedIdeaArrayList);
        adapter.notifyDataSetChanged();
    }

    public void parseResponse(String response) {
        // remove {}
        response = response.replaceAll("[{}\"]","");

        int ideaDataLength = 10;

        String[] pairs = response.split(",");
        for (int i = 0; i < pairs.length; i += ideaDataLength) {
            Idea idea = new Idea();
            Brain author = new Brain();

            for (int j = i; j < i + ideaDataLength; j++) {
                String[] keyValue = pairs[j].split(":");
                int length = keyValue.length - 1;

                if (keyValue[0].equals("id")) {
                    idea.setId(Long.valueOf(keyValue[length]));
                }
                if (keyValue[0].equals("citeId")) {
                    idea.setCiteId(keyValue[length]);
                }
                else if (keyValue[0].equals("title")) {
                    idea.setTitle(keyValue[length]);
                }
                else if (keyValue[0].equals("context")) {
                    idea.setContext(keyValue[length]);
                }
                else if (keyValue[0].equals("content")) {
                    idea.setContent(keyValue[length]);
                }
                else if (keyValue[0].equals("author")) {
                    author.setEmail(keyValue[length]);
                }
                else if (keyValue[0].equals("username")) {
                    author.setUsername(keyValue[length]);
                }
                else if (keyValue[0].equals("password")) {
                    author.setPassword(keyValue[length]);
                }
                else if (keyValue[0].equals("firstName")) {
                    author.setFirstName(keyValue[length]);
                }
                else if (keyValue[0].equals("lastName")) {
                    author.setLastName(keyValue[length]);
                }
            } // end j-loop

            idea.setAuthor(author);
            ideaArrayList.add(idea);
        } // end i-loop

        searchedIdeaArrayList.clear();
        searchedIdeaArrayList.addAll(ideaArrayList);
        Collections.reverse(searchedIdeaArrayList);
        adapter.notifyDataSetChanged();
    }

    /*
        IdeaCallback
    */
    @Override
    public void deleteItem(int position, Object object) {}

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