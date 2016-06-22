package com.cremy.shared.data;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import rx.Subscriber;

/**
 * This handler allows to wrap the firebase listeners (OnSuccessListener, OnFailureListener, OnCompleteListener)
 * With a RxJava Subscriber
 * @param <T>
 */
public class FirebaseRxSubscriberWrapper<T>
        implements OnSuccessListener<T>,
        OnFailureListener,
        OnCompleteListener<T> {

    private final Subscriber<? super T> subscriber;

    private FirebaseRxSubscriberWrapper(Subscriber<? super T> observer) {
        this.subscriber = observer;
    }

    public static <T> void assignOnTask(Subscriber<? super T> observer, Task<T> task) {
        FirebaseRxSubscriberWrapper wrapper = new FirebaseRxSubscriberWrapper(observer);
        task.addOnSuccessListener(wrapper)
                .addOnFailureListener(wrapper)
                .addOnCompleteListener(wrapper);
    }


    public static <T> void assignOnTaskWithoutCompletedListener(Subscriber<? super T> observer, Task<T> task) {
        FirebaseRxSubscriberWrapper handler = new FirebaseRxSubscriberWrapper(observer);
        task.addOnSuccessListener(handler)
                .addOnFailureListener(handler);
    }

    @Override
    public void onSuccess(T res) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onNext(res);
        }
    }

    @Override
    public void onComplete(@NonNull Task<T> task) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onError(e);
        }
    }
}