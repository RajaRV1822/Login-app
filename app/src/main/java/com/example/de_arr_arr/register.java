package com.example.de_arr_arr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner1;
    private Button regbtn1;
    private EditText username1,age1,email1,Password1;
    private ProgressBar progressBar1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        banner1 = (TextView) findViewById(R.id.banner);
        banner1.setOnClickListener(this);

        regbtn1 = (Button) findViewById(R.id.regbtn);
        regbtn1.setOnClickListener(this);

        username1 = (EditText) findViewById(R.id.username);
        age1 = (EditText) findViewById(R.id.age);
        email1 = (EditText) findViewById(R.id.email);
        Password1 = (EditText) findViewById(R.id.Password);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.regbtn:
                regbtn1();
                break;
        }

    }

    private void regbtn1() {
        String email = email1.getText().toString().trim();
        String username = username1.getText().toString().trim();
        String Password = Password1.getText().toString().trim();
        String age = age1.getText().toString().trim();
        if (username.isEmpty()){
            username1.setError("Username is required");
            username1.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            age1.setError("Age is required");
            age1.requestFocus();
            return;
        }
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
        mAuth.createUserWithEmailAndPassword(email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(username,Password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(register.this,"User has been registered in sucessfully",Toast.LENGTH_LONG).show();
                                        progressBar1.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(register.this,"Failed to register",Toast.LENGTH_LONG).show();
                                        progressBar1.setVisibility(View.GONE);
                                    }

                                }
                            });
                        }else{
                            Toast.makeText(register.this,"Failed to register",Toast.LENGTH_LONG).show();
                            progressBar1.setVisibility(View.GONE);
                        }

                    }
                });





    }
}