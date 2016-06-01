package com.cremy.shared.data.remote;

import com.cremy.shared.data.FirebaseRxHandler;
import com.cremy.shared.data.model.User;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by remychantenay on 18/05/2016.
 */
public class AuthService extends BaseFirebaseDatabaseService {


    public AuthService(FirebaseDatabase _firebaseDatabase,
                       FirebaseAuth _firebaseAuth) {
        super(_firebaseDatabase, _firebaseAuth);
    }

    /**
     * Allows to _create_ a user with a given email address and password
     * @param _email
     * @param _password
     */
    public Observable<AuthResult> createUserWithEmailAndPassword(final String _email,
                                               final String _password) {
        return Observable.create(new Observable.OnSubscribe<AuthResult>() {
            @Override
            public void call(final Subscriber<? super AuthResult> subscriber) {
                FirebaseRxHandler.assignOnTask(subscriber, firebaseAuth.createUserWithEmailAndPassword(_email, _password));
            }
        });
    }

    /**
     * Allows to _signin_ a user with a given email address and password
     * @param _email
     * @param _password
     */
    public Observable<AuthResult> signInWithEmailAndPassword(final String _email,
                                                             final String _password) {
        return Observable.create(new Observable.OnSubscribe<AuthResult>() {
            @Override
            public void call(final Subscriber<? super AuthResult> subscriber) {
                FirebaseRxHandler.assignOnTask(subscriber, firebaseAuth.signInWithEmailAndPassword(_email, _password));
            }
        });
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
    public void writeUserInDatabase(final String _userId,
                                    final String _name,
                                    ValueEventListener _valueEventListener) {

        User user = new User(_name);

        DatabaseReference targetChild = this.firebaseDatabase.
                getReference()
                .child(FIREBASE_CHILD_KEY_USERS)
                .child(_userId);

        targetChild.addListenerForSingleValueEvent(_valueEventListener);
        targetChild.setValue(user);
    }

}