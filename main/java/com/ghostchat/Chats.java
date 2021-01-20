package com.ghostchat;
import androidx.annotation.NonNull;

public class Chats {
    private String chatWith;
    private String picture;
    private String message;
    private String time;

    public Chats(@NonNull String chatWith, @NonNull String picture, @NonNull String message, @NonNull String time){
        this.chatWith = chatWith;
        this.picture = picture;
        this.message = message;
        this.time = time;
    }

    public String getChatWith() {
        return chatWith;
    }

    public String getPicture() {
        return picture;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public void setChatWith(String chatWith) {
        this.chatWith = chatWith;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Chat: " + this.chatWith + " , message: " + this.message + " , at:" + this.time;
    }
}
