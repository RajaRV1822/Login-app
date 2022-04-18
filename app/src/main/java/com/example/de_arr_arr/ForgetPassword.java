package com.example.de_arr_arr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email1;
    private Button passreset;
    private ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email1 = (EditText) findViewById(R.id.email);
        passreset = (Button) findViewById(R.id.fp);
        mAuth = FirebaseAuth.getInstance();
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar);

        passreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passreset();
            }

            private void passreset() {
                String email = email1.getText().toString().trim();

                if (email.isEmpty()){
                    email1.setError("Email is required");
                    email1.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    email1.setError("Please provide a valid email address!!!");
                    email1.requestFocus();
                    return;
                }

                progressBar1.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(ForgetPassword.this,"Check Email for reset mail",Toast.LENGTH_LONG).show();
                            progressBar1.setVisibility(View.GONE);
                        }else{
                            Toast.makeText(ForgetPassword.this,"Failed to reset password",Toast.LENGTH_LONG).show();
                            progressBar1.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });


    }
}