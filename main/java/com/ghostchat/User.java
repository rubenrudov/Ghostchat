package com.ghostchat;
// Ruby Rudov 22/11/2020
// Class for user description and actions that requires profile information

public class User {
    private String id;
    private String username;
    private String profileImage;


    public User() {
        this.id = "defaultId";
        this.username = "defaultUsername";
        this.profileImage = "defaultPicture";
    }

    public User(String id, String username, String profileImage){
        this.id = id;
        this.username = username;
        this.profileImage = profileImage;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    @Override
    public String toString() {
        return "ID: " + id + " username: " + username + " image: " + profileImage;
    }

    public boolean equals(User other){
        return this.toString().equals(other.toString());
    }
}
