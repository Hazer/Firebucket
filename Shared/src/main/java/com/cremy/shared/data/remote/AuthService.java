package com.cremy.shared.data.remote;

import com.cremy.shared.data.FirebaseRxSingle;
import com.cremy.shared.data.model.Bucket;
import com.cremy.shared.data.model.User;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;

/**
 * Created by remychantenay on 18/05/2016.
 */
public class AuthService extends BaseFirebaseDatabaseService {


    @Inject
    public AuthService(FirebaseDatabase _firebaseDatabase,
                       FirebaseAuth _firebaseAuth) {
        super(_firebaseDatabase, _firebaseAuth);
    }

    /**
     * Allows to _create_ a user with a given email address and password
     * @param _email
     * @param _password
     */
    public Single<AuthResult> createUserWithEmailAndPassword(final String _email,
                                               final String _password) {
        return FirebaseRxSingle.getSingle(firebaseAuth.createUserWithEmailAndPassword(_email, _password));
    }

    /**
     * Allows to _signin_ a user with a given email address and password
     * @param _email
     * @param _password
     */
    public Single<AuthResult> signInWithEmailAndPassword(final String _email,
                                                             final String _password) {
        return FirebaseRxSingle.getSingle(firebaseAuth.signInWithEmailAndPassword(_email, _password));
    }

    /**
     * Allows to know if the current user already exists or not
     * @return
     */
    public boolean ifUserExists() {
        return this.firebaseAuth.getCurrentUser()!=null;
    }


    /**
     * Allows to write a user in the database
     * @param _userId
     * @param _name
     */
    public Observable<Bucket> writeUserInDatabase(final String _userId,
                                                  final String _name) {

        User user = new User(_name);

        DatabaseReference targetChild = this.firebaseDatabase.
                getReference()
                .child(FIREBASE_CHILD_KEY_USERS)
                .child(_userId);

        targetChild.setValue(user);
        return observeSingleValue(targetChild, Bucket.class);

/*        targetChild.addListenerForSingleValueEvent(_valueEventListener);
        targetChild.setValue(user);*/
    }

}