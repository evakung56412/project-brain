package com.man_jou.projectbrain.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.man_jou.projectbrain.R;
import com.man_jou.projectbrain.callback.ApiCallback;
import com.man_jou.projectbrain.form.CiteIdeaForm;
import com.man_jou.projectbrain.form.NewIdeaForm;
import com.man_jou.projectbrain.model.Idea;
import com.man_jou.projectbrain.rest.PostTaskJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CiteIdeaFragment extends Fragment implements ApiCallback<Idea> {

    private EditText newIdeaTitleET, newIdeaContextET, newIdeaContentET;

    private String username, citeId, title;

    public CiteIdeaFragment getFragment() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        username = getArguments().getString("username");
        citeId = getArguments().getString("citeId");
        title = getArguments().getString("title");
        return inflater.inflate(R.layout.fragment_new_idea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newIdeaTitleET = view.findViewById(R.id.newIdeaTitleET);
        newIdeaContextET = view.findViewById(R.id.newIdeaContextET);
        newIdeaContentET = view.findViewById(R.id.newIdeaContentET);

        newIdeaContextET.setText(title);
        newIdeaContextET.setEnabled(false);

        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.toolbar_newIdea);
        TextView textView = toolbar.findViewById(R.id.toolbarTV);
        textView.setText("Cite A New Todo");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_new_idea, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_saveIdea) {

            if (newIdeaTitleET.getText().toString().equals("") ||
                newIdeaContentET.getText().toString().equals("")
               )
            {
                Toast.makeText(getContext(), "Somewhere is empty, please enter it.", Toast.LENGTH_SHORT).show();
            }
            else {
                CiteIdeaForm form = new CiteIdeaForm();
                form.setUsername(username);
                form.setCiteId(citeId);
                form.setTitle(newIdeaTitleET.getText().toString());
                form.setContext(newIdeaContextET.getText().toString());
                form.setContent(newIdeaContentET.getText().toString());

                new PostTaskJson<CiteIdeaForm, Idea>(Idea.class, getFragment()).execute(form);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void postResult(ResponseEntity<Idea> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Toast.makeText(getContext(), "Cite Successfully!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getContext(), "Cite Failed! There has some mistakes: " + responseEntity.getStatusCode().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getResult(ResponseEntity<Idea> responseEntity) {}
}
