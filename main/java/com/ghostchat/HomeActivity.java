package com.ghostchat;
// Ruby Rudov 22/11/2020
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    DatabaseReference databaseReference, ref1;
    FirebaseUser firebaseUser;
    ListView listView;
    ArrayList<User> chats;
    ChatsAdapter chatsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        chats = new ArrayList<>();
        listView = findViewById(R.id.listView);
        ref1 = FirebaseDatabase.getInstance().getReference("Users");
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    User u = dsp.getValue(User.class);
                    assert u != null;
                    if(!(firebaseUser.getUid().equals(u.getId())))
                        chats.add(u);
                }
                chatsAdapter = new ChatsAdapter(getBaseContext(), R.layout.chats_item, chats);
                listView.setAdapter(chatsAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                assert u != null;
                Objects.requireNonNull(getSupportActionBar()).setTitle("Hey " + u.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
