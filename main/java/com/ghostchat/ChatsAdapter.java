package com.ghostchat;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.List;

public class ChatsAdapter extends ArrayAdapter<User> {
    private int resourceLayout;
    private Context context;
    ChatsAdapter(Context context, int resource, List<User> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resourceLayout, null);
        }
        final User u = getItem(position);
        if (u != null) {
            TextView name = v.findViewById(R.id.usernameDisplay);
            ImageView profileImg = v.findViewById(R.id.profileImg);
            if (name != null) {
                name.setText(u.getUsername());
            }
            profileImg.setImageResource(R.mipmap.ic_launcher);
            // TODO: Set manual profile picture later... (Glide...)
        } // Can be replaced with an "assert clause"
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert u != null;
                Intent i = new Intent(context, ChatActivity.class);
                i.putExtra("Uid", u.getId());
                context.startActivity(i);
            }
        });
        return v;
    }
}

