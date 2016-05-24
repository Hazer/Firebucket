package com.cremy.shared.data.model;

public class User {

    public final static int RULE_LOGIN_PASSWORD_MIN_CHARS = 10;

    //region Testing-purpose only
    public final static String USER_TESTING_EMAIL = "test@test.com";

    public final static String USER_TESTING_PASSWORD_SUCCESS = "testpassword";
    public final static String USER_TESTING_PASSWORD_FAIL = "failpassword";
    //endregion

    //region Variables
    public String username;
    //endregion

    //region Constructors
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String username) {
        this.username = username;
    }
    //endregion

    //region Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    //endregion
}