package com.cremy.shared.data.remote;

import com.cremy.shared.data.model.TagList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import rx.Single;

/**
 * Created by remychantenay on 18/05/2016.
 */
public class TagListService extends BaseFirebaseDatabaseService {

    public DatabaseReference getChildReference() {
        this.childReference = this.firebaseDatabase.
                getReference()
                .child(FIREBASE_CHILD_KEY_TAG_LIST);

        return this.childReference;
    }

    @Inject
    public TagListService(FirebaseDatabase _firebaseDatabase,
                          FirebaseAuth _firebaseAuth) {
        super(_firebaseDatabase, _firebaseAuth);
    }


    //region Tags
    public Single<TagList> getTagList() {
        return observeSingleValue(this.getChildReference(), TagList.class);
    }
    //endregion
}