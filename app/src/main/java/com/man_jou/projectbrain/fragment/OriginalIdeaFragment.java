package com.man_jou.projectbrain.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.man_jou.projectbrain.R;
import com.man_jou.projectbrain.callback.ApiCallback;
import com.man_jou.projectbrain.model.Idea;
import com.man_jou.projectbrain.rest.GetTaskJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class OriginalIdeaFragment extends Fragment implements ApiCallback<Idea> {

    private TextView originalIdeaTitleTV, originalIdeaContextTV, originalIdeaContentTV, originalIdeaAuthorTV;

    private String citeId;

    public OriginalIdeaFragment getFragment() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        citeId = getArguments().getString("citeId");
        return inflater.inflate(R.layout.fragment_original_idea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        originalIdeaTitleTV = view.findViewById(R.id.originalIdeaTitleTV);
        originalIdeaContextTV = view.findViewById(R.id.originalIdeaContextTV);
        originalIdeaContentTV = view.findViewById(R.id.originalIdeaContentTV);
        originalIdeaAuthorTV = view.findViewById(R.id.originalIdeaAuthorTV);

        String request = "http://10.0.2.2:8080/idea/" + citeId;
        new GetTaskJson<>(Idea.class, getFragment()).execute(request);
    }

    @Override
    public void postResult(ResponseEntity<Idea> responseEntity) {}

    @Override
    public void getResult(ResponseEntity<Idea> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK)
        {
            Idea idea = responseEntity.getBody();
            originalIdeaTitleTV.setText(idea.getTitle());
            originalIdeaContextTV.setText(idea.getContext());
            originalIdeaContentTV.setText(idea.getContent());
            originalIdeaAuthorTV.setText("Author: " + idea.getAuthor().getUsername());
        }
        else {
            Log.i("Get Ori_Idea Failed", responseEntity.getStatusCode().toString());
        }
    }
}