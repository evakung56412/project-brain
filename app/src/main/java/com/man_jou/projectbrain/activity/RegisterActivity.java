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
import com.man_jou.projectbrain.form.RegisterForm;
import com.man_jou.projectbrain.model.Brain;
import com.man_jou.projectbrain.rest.PostTaskJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RegisterActivity extends AppCompatActivity implements ApiCallback<Brain> {

    private EditText registerEmailET, registerUsernameET, registerPwdET, registerFirstNameET, registerLastNameET;

    public RegisterActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmailET = findViewById(R.id.registerEmailET);
        registerUsernameET = findViewById(R.id.registerUsernameET);
        registerPwdET = findViewById(R.id.registerPwdET);
        registerFirstNameET = findViewById(R.id.registerFirstNameET);
        registerLastNameET = findViewById(R.id.registerLastNameET);

        setupButton();
    }

    public void setupButton() {
        Button signUpBtn = findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerEmailET.getText().toString().equals("")    ||
                    registerUsernameET.getText().toString().equals("") ||
                    registerPwdET.getText().toString().equals("")
                   )
                {
                    Toast.makeText(v.getContext(), "Somewhere is empty, please enter it.", Toast.LENGTH_SHORT).show();
                }
                else {
                    RegisterForm registerForm = new RegisterForm();
                    registerForm.setEmail(registerEmailET.getText().toString());
                    registerForm.setUsername(registerUsernameET.getText().toString());
                    registerForm.setPassword(registerPwdET.getText().toString());
                    registerForm.setFirstName(registerFirstNameET.getText().toString());
                    registerForm.setLastName(registerLastNameET.getText().toString());

                    new PostTaskJson<RegisterForm, Brain>(Brain.class, getActivity()).execute(registerForm);
                }
            }
        });

        Button signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
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
        if (responseEntity.getStatusCode() == HttpStatus.OK)
        {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            i.putExtra("username", responseEntity.getBody().getUsername());
            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(this, "Register Failed! There has some mistakes: " + responseEntity.getStatusCode().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getResult(ResponseEntity<Brain> responseEntity) {}
}