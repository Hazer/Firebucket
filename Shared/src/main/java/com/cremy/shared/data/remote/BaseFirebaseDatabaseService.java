package com.cremy.shared.data.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by remychantenay on 24/05/2016.
 */
public class BaseFirebaseDatabaseService {

    public final static String FIREBASE_CHILD_KEY_USERS = "users";
    public final static String FIREBASE_CHILD_KEY_TASKS = "tasks";

    protected FirebaseDatabase firebaseDatabase;
    protected FirebaseAuth firebaseAuth;

    public BaseFirebaseDatabaseService(FirebaseDatabase _firebaseDatabase,
                                       FirebaseAuth _firebaseAuth) {
        this.firebaseDatabase = _firebaseDatabase;
        this.firebaseAuth = _firebaseAuth;
    }

}
