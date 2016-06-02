package com.cremy.shared.data.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by remychantenay on 18/05/2016.
 */
public class BucketService extends BaseFirebaseDatabaseService {

    public BucketService(FirebaseDatabase _firebaseDatabase,
                         FirebaseAuth _firebaseAuth) {
        super(_firebaseDatabase, _firebaseAuth);

        this.childReference = this.firebaseDatabase.
                getReference()
                .child(FIREBASE_CHILD_KEY_USERS)
                .child(this.firebaseAuth.getCurrentUser().getUid());
    }


    //region Bucket
    public void startListening(ValueEventListener _valueEventListener) {
        this.childReference.addValueEventListener(_valueEventListener);
    }

    public void stopListening(ValueEventListener _valueEventListener) {
        this.childReference.removeEventListener(_valueEventListener);
    }
    //endregion
}