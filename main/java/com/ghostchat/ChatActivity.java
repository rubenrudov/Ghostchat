package com.ghostchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


// TODO: Make the page from the last messages to the first messages (re-turn it)
public class ChatActivity extends AppCompatActivity {
    Intent i;
    String uid;
    ImageView profileImg; // TODO: Add profile picture in the app bar
    FirebaseUser firebaseUser;
    EditText message;
    ImageButton emotesOpen, send;
    List<PrivateMessage> messages;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        i = getIntent();
        uid = i.getStringExtra("Uid");
        recyclerView = findViewById(R.id.recycler);
        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messages);
        getPrevMessages(firebaseUser.getUid(), uid);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
        FirebaseDatabase.getInstance().getReference("Users").child(uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User u = snapshot.getValue(User.class);
                        if (u != null){
                            Objects.requireNonNull(getSupportActionBar()).setTitle(u.getUsername());
                        } // Can be replaced with an "assert clause"
                        // TODO: Set profile picture via Glide lib (Gradle lib..)
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
        message = findViewById(R.id.message);
        send = findViewById(R.id.sendButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText temp = message;
                String message = temp.getText().toString();
                if(message.equals("")){
                    temp.setHint("הקלד הודעה לפני השליחה !");
                }
                else{
                    sendMessage(uid, firebaseUser.getUid(), message);
                    temp.setText("");
                }
            }
        });
    }

    void sendMessage(String to, String sent, String message){
        // Inserts the new message into the DB
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("to", to);
        hashMap.put("sent", sent);
        hashMap.put("message", message);
        databaseReference.child("Messages").push().setValue(hashMap);
        getPrevMessages(firebaseUser.getUid(), uid);
    }

    void refreshAdapter(){
        // After every update of the messages list, the adapter will be refreshed
        messageAdapter = new MessageAdapter(ChatActivity.this, messages);
        recyclerView.setAdapter(messageAdapter);
    }

    private void getPrevMessages(final String me, final String chatWith){
        messages = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Messages");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    PrivateMessage privateMessage = dataSnapshot.getValue(PrivateMessage.class);
                    assert privateMessage != null;
                    if(privateMessage.getTo().equals(me) && privateMessage.getSent().equals(chatWith))
                    {
                        messages.add(privateMessage);
                        refreshAdapter();
                    }
                    else if(privateMessage.getTo().equals(chatWith) && privateMessage.getSent().equals(me))
                    {
                        messages.add(privateMessage);
                        refreshAdapter();
                    }
                    else {
                        refreshAdapter();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                refreshAdapter();
            }
        });
    }
}
