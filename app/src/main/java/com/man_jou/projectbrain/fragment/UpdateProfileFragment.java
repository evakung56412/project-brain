package com.man_jou.projectbrain.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.man_jou.projectbrain.R;
import com.man_jou.projectbrain.callback.ApiCallback;
import com.man_jou.projectbrain.form.UpdateProfileForm;
import com.man_jou.projectbrain.model.Brain;
import com.man_jou.projectbrain.rest.GetTaskJson;
import com.man_jou.projectbrain.rest.PostTaskJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UpdateProfileFragment extends Fragment implements ApiCallback<Brain> {

    private EditText updatePwdET, updateFirstNameET, updateLastNameET;

    private String username;

    public UpdateProfileFragment getFragment() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        username = getArguments().getString("username");
        return inflater.inflate(R.layout.fragment_update_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updatePwdET = view.findViewById(R.id.updatePwdET);
        updateFirstNameET = view.findViewById(R.id.updateFirstNameET);
        updateLastNameET = view.findViewById(R.id.updateLastNameET);

        // GET
        String request = "http://10.0.2.2:8080/brain?username=" + username;
        new GetTaskJson<>(Brain.class, getFragment()).execute(request);

        // POST
        Button updateBtn = view.findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateProfileForm form = new UpdateProfileForm();
                form.setUsername(username);
                form.setPassword(updatePwdET.getText().toString());
                form.setFirstName(updateFirstNameET.getText().toString());
                form.setLastName(updateLastNameET.getText().toString());

                new PostTaskJson<UpdateProfileForm, Brain>(Brain.class, getFragment()).execute(form);
            }
        });
    }

    /*
        ApiCallback<Brain>
    */
    @Override
    public void postResult(ResponseEntity<Brain> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK)
        {
            Toast.makeText(getContext(), "Update Profile Successfully!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getContext(), "Update Profile Failed! There has some mistakes: " + responseEntity.getStatusCode().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getResult(ResponseEntity<Brain> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK)
        {
            Brain brain = responseEntity.getBody();
            updateFirstNameET.setText(brain.getFirstName());
            updateLastNameET.setText(brain.getLastName());
        }
        else {
            updatePwdET.setText("Error");
            updateFirstNameET.setText("Error");
            updateLastNameET.setText("Error");
            Toast.makeText(getContext(), "Get Profile Failed! There has some mistakes: " + responseEntity.getStatusCode().toString(), Toast.LENGTH_LONG).show();
        }
    }
}