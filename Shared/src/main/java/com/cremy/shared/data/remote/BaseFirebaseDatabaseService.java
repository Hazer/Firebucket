package com.cremy.shared.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cremy.shared.exceptions.FirebaseRxDataCastException;
import com.cremy.shared.exceptions.FirebaseRxDataException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by remychantenay on 24/05/2016.
 */
public class BaseFirebaseDatabaseService {
    private final static String TAG = "BaseFirebaseDatabase";

    public final static String FIREBASE_CHILD_KEY_USERS = "users";
    public final static String FIREBASE_CHILD_KEY_TASKS = "tasks";
    public final static String FIREBASE_CHILD_KEY_TAGS = "tags";

    protected FirebaseDatabase firebaseDatabase;
    protected FirebaseAuth firebaseAuth;

    /**
     * The target node for a given service
     */
    protected DatabaseReference childReference;

    public BaseFirebaseDatabaseService(FirebaseDatabase _firebaseDatabase,
                                       FirebaseAuth _firebaseAuth) {
        this.firebaseDatabase = _firebaseDatabase;
        this.firebaseAuth = _firebaseAuth;
    }


    @NonNull
    public static <T> Single<T> observeSingleValue(@NonNull final Query query, @NonNull final Class<T> clazz) {
        return Single.create(new Single.OnSubscribe<T>() {
            @Override
            public void call(final SingleSubscriber<? super T> subscriber) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG, dataSnapshot.toString());
                        T value = dataSnapshot.getValue(clazz);
                        if (value != null) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onSuccess(value);
                            }
                        } else {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onError(new FirebaseRxDataCastException("Unable to cast firebase data response to " + clazz.getSimpleName()));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(new FirebaseRxDataException(error));
                        }
                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    public static <T> Single<T> observeSingleValue(@NonNull final Task<T> task) {
        return Single.create(new Single.OnSubscribe<T>() {
            @Override
            public void call(final SingleSubscriber<? super T> subscriber) {
                task.addOnSuccessListener(new OnSuccessListener<T>() {
                    @Override
                    public void onSuccess(T result) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onSuccess(result);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


    @NonNull
    public static <T> Observable<List<T>> observeValuesList(@NonNull final Query query, @NonNull final Class<T> clazz) {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(final Subscriber<? super List<T>> subscriber) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG, dataSnapshot.toString());
                        List<T> items = new ArrayList<T>();
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            T value = childSnapshot.getValue(clazz);
                            if (value == null) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onError(new FirebaseRxDataCastException("Unable to cast firebase data response to " + clazz.getSimpleName()));
                                }
                            } else {
                                items.add(value);
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(items);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(new FirebaseRxDataException(error));
                        }
                    }
                });
            }

        });
    }

    @NonNull
    public static <T> Observable<Map<String, T>> observeValuesMap(@NonNull final Query query, @NonNull final Class<T> clazz) {
        return Observable.create(new Observable.OnSubscribe<Map<String, T>>() {
            @Override
            public void call(final Subscriber<? super Map<String, T>> subscriber) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, T> items = new HashMap<String, T>();
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            T value = childSnapshot.getValue(clazz);
                            if (value == null) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onError(new FirebaseRxDataCastException("unable to cast firebase data response to " + clazz.getSimpleName()));
                                }
                            } else {
                                items.put(childSnapshot.getKey(), value);
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(items);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(new FirebaseRxDataException(error));
                        }
                    }
                });
            }

        });
    }

}
