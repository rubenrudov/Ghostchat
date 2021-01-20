package com.ghostchat;
// Ruby Rudov 22/11/2020
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button submit;
    EditText emailEt, passwordEt;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        submit = findViewById(R.id.submit);
        // Implementation of View.OnClickListener interface
        submit.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Login");
    }


    protected void login(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class).addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
                            ));
                        }
                        finish();
                    }
                }
        );
    }

    // For @ amount validator
    private boolean strudelCount(String s){
        int i = 0;
        for (Character c: s.toCharArray()){
            if(c == '@'){
                i++;
            }
        }
        return i == 1;
    }

    @Override
    public void onClick(View v) {
        if (v == submit){
            String email = emailEt.getText().toString();
            String password = passwordEt.getText().toString();
            if (email.length() < 11){
                Toast.makeText(getBaseContext(), "Type a valid password (8-20 chars)", Toast.LENGTH_LONG).show();
            }else if (password.length() < 8 || password.length() > 20) {
                Toast.makeText(getBaseContext(), "Type a valid password (8-20 chars)", Toast.LENGTH_LONG).show();
            } else if (!strudelCount(email)) {
                Toast.makeText(getBaseContext(), "Only one @ is allowed", Toast.LENGTH_LONG).show();
            } else if (email.startsWith(".") || email.endsWith(".")){
                Toast.makeText(getBaseContext(), ". isn't allowed in the beginning or the end of an email", Toast.LENGTH_LONG).show();
            }
            else {
                login(email, password);
            }
        }
    }
}
