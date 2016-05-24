package com.cremy.shared.data.model;

@IgnoreExtraProperties
public class User {

    public String username;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username) {
        this.username = username;
    }

}
// [END blog_user_class]