package com.cremy.shared.data.remote;

import com.cremy.shared.data.model.Bucket;
import com.cremy.shared.data.model.TagList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;

/**
 * Created by remychantenay on 18/05/2016.
 */
public class BucketService extends BaseFirebaseDatabaseService {

    public DatabaseReference getChildReference() {
        this.childReference = this.firebaseDatabase.
                getReference()
                .child(FIREBASE_CHILD_KEY_USERS)
                .child(this.firebaseAuth.getCurrentUser().getUid());

        return this.childReference;
    }

    @Inject
    public BucketService(FirebaseDatabase _firebaseDatabase,
                         FirebaseAuth _firebaseAuth) {
        super(_firebaseDatabase, _firebaseAuth);
    }


    //region Bucket
    public Observable<Bucket> getBucket() {
        return observeValue(this.getChildReference(), Bucket.class);
    }
    //endregion
}