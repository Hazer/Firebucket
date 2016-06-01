package com.cremy.shared.data.remote;

import com.cremy.shared.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
     * @param _onCompleteListener
     */
    public void createUserWithEmailAndPassword(final String _email,
                                               final String _password,
                                               OnCompleteListener _onCompleteListener) {
        this.firebaseAuth.createUserWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(_onCompleteListener);
    }

    /**
     * Allows to _signin_ a user with a given email address and password
     * @param _email
     * @param _password
     * @param _onCompleteListener
     */
    public void signInWithEmailAndPassword(final String _email,
                                               final String _password,
                                               OnCompleteListener _onCompleteListener) {
        this.firebaseAuth.signInWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(_onCompleteListener);
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