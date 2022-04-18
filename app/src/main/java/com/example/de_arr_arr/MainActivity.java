package com.example.de_arr_arr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView reg , fpass ;
    private EditText email1,Password1;
    private FirebaseAuth mAuth;
    private Button loginbtn1;
    private ProgressBar progressBar1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reg = (TextView) findViewById(R.id.reg);
        reg.setOnClickListener(this);

        fpass = (TextView) findViewById(R.id.fp);
        fpass.setOnClickListener(this);

        loginbtn1 = (Button) findViewById(R.id.loginbtn);
        loginbtn1.setOnClickListener(this);

        email1 = (EditText) findViewById(R.id.email);
        Password1 = (EditText) findViewById(R.id.Password);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reg:
                startActivity(new Intent(this,register.class));
                break;
            case R.id.loginbtn:
                userLogin();
                break;
            case R.id.fp:
                startActivity(new Intent(this,ForgetPassword.class));
                break;
        }
    }

    private void userLogin() {

        String email = email1.getText().toString().trim();
        String Password = Password1.getText().toString().trim();

        if (email.isEmpty()){
            email1.setError("Email is required");
            email1.requestFocus();
            return;
        }
        if (Password.isEmpty()){
            Password1.setError("Password is required");
            Password1.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email1.setError("Please provide a valid email address!!!");
            email1.requestFocus();
            return;
        }
        if (Password.length() < 6){
            Password1.setError("Password mustnot be less than 6 characters");
            Password1.requestFocus();
            return;
        }

        progressBar1.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,mrdonline.class));

                }else {
                    Toast.makeText(MainActivity.this,"Failed to login in please check your cardentials..", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}