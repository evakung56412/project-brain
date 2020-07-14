package com.man_jou.projectbrain.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.man_jou.projectbrain.R;
import com.man_jou.projectbrain.callback.ApiCallback;
import com.man_jou.projectbrain.form.LoginForm;
import com.man_jou.projectbrain.model.Brain;
import com.man_jou.projectbrain.rest.PostTaskJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MainActivity extends AppCompatActivity implements ApiCallback<Brain> {

    private EditText loginEmailET, loginPwdET;

    public MainActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmailET = findViewById(R.id.loginEmailET);
        loginPwdET = findViewById(R.id.loginPwdET);

        setupButton();
    }

    public void setupButton() {
        final Button loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginEmailET.getText().toString().equals("") ||
                    loginPwdET.getText().toString().equals("")
                   )
                {
                    Toast.makeText(v.getContext(), "Your email or password is empty, please enter it.", Toast.LENGTH_SHORT).show();
                }
                else {
                    LoginForm loginForm = new LoginForm();
                    loginForm.setEmail(loginEmailET.getText().toString());
                    loginForm.setPassword(loginPwdET.getText().toString());
                    new PostTaskJson<LoginForm, Brain>(Brain.class, getActivity()).execute(loginForm);
                }
            }
        });

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    /*
        ApiCallback<Brain>
    */
    @Override
    public void postResult(ResponseEntity<Brain> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            i.putExtra("username", responseEntity.getBody().getUsername());
            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(this, "Login Failed! Your username or password is wrong.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getResult(ResponseEntity<Brain> responseEntity) {}
}