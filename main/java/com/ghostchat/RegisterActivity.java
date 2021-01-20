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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    Button submit;
    EditText emailEt, usernameEt, passwordEt;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailEt = findViewById(R.id.emailEt);
        usernameEt = findViewById(R.id.usernameEt);
        passwordEt = findViewById(R.id.passwordEt);
        submit = findViewById(R.id.submit);
        // Implementation of View.OnClickListener interface
        submit.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Registration");
    }

    // Register function;
    protected void register(final String email, final String username, final String password){
        firebaseAuth.createUserWithEmailAndPassword(
                email,
                password
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String id = Objects.requireNonNull(firebaseUser).getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", id);
                    hashMap.put("email", email);
                    hashMap.put("username", username);
                    hashMap.put("password", password);
                    hashMap.put("image", "default");
                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(
                                        new Intent(RegisterActivity.this, HomeActivity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            }
                            finish();
                        }
                    });
                }
            }
        });
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
            String username = usernameEt.getText().toString();
            String password = passwordEt.getText().toString();
            if (email.length() < 11)
            {
                Toast.makeText(getBaseContext(), "Type a valid email", Toast.LENGTH_LONG).show();
            } else if (username.length() < 2) {
                Toast.makeText(getBaseContext(), "Type a valid username", Toast.LENGTH_LONG).show();
            } else if (password.length() < 8 || password.length() > 20) {
                Toast.makeText(getBaseContext(), "Type a valid password (8-20 chars)", Toast.LENGTH_LONG).show();
            } else if (!strudelCount(email)) {
                Toast.makeText(getBaseContext(), "Only one @ is allowed", Toast.LENGTH_LONG).show();
            } else if (email.startsWith(".") || email.endsWith(".")){
                Toast.makeText(getBaseContext(), ". isn't allowed in the beginning or the end of an email", Toast.LENGTH_LONG).show();
            }
            else {
                register(email, username, password);
            }
        }
    }
}
