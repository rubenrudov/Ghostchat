package com.ghostchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context c;
    private List<PrivateMessage> messages;

    MessageAdapter(Context c, List<PrivateMessage> messages) {
        this.c = c;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // return new MessageAdapter.ViewHolder(LayoutInflater.from(c).inflate(R.layout.chats_item, parent, false));
        if (viewType == 1) {
            // Sent by the user to the other
            return new MessageAdapter.ViewHolder(LayoutInflater.from(c).inflate(R.layout.message_as_sender, parent, false));
        } else {
            // Sent by the other to the user
            return new MessageAdapter.ViewHolder(LayoutInflater.from(c).inflate(R.layout.message_as_receiver, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int index) {
        PrivateMessage p = messages.get(index);
        holder.message.setText(p.getMessage());
    }

    @Override
    public int getItemCount() {
        return this.messages.size();
    }

    @Override
    public int getItemViewType(int index) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        return messages.get(index).getSent().equals(user.getUid()) ? 1 : 0;
        // If the user id of the sender of the message in the current index is the user - return 1;
        // Else - return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
        }
    }
}
