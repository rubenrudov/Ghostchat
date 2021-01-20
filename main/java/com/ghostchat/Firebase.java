package com.ghostchat;

// import com.google.firebase.auth.FirebaseUser;
// import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Firebase {
    public static FirebaseUser firebaseUser;
    public static DatabaseReference databaseReference;
    /**
     *  Class for Database operations and automations
     */
    public static void sendMessage(String message, String userName){
        // TODO: save message to db
        // TODO: create file (reference) messages in firebase (Line 24)
        // write message
        databaseReference = FirebaseDatabase.getInstance().getReference("Messages").child(userName);
    }
}