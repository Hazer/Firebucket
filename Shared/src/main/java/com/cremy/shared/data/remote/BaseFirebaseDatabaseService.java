package com.cremy.shared.data.remote;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by remychantenay on 24/05/2016.
 */
public class BaseFirebaseDatabaseService {

    protected FirebaseDatabase firebaseDatabase;

    public BaseFirebaseDatabaseService(FirebaseDatabase _firebaseDatabase) {
        this.firebaseDatabase = _firebaseDatabase;
    }

}
