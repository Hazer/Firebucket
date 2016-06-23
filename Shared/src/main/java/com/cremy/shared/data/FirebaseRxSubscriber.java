package com.cremy.shared.data;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import rx.Subscriber;

/**
 * Allows to "wrap" the Firebase listeners (OnSuccessListener, OnFailureListener, OnCompleteListener)
 * With a RxJava {@link Subscriber}
 * @param <T>
 */
public class FirebaseRxSubscriber<T>
        implements OnSuccessListener<T>, OnFailureListener, OnCompleteListener<T> {

    private final Subscriber<? super T> subscriber;

    private FirebaseRxSubscriber(Subscriber<? super T> observer) {
        this.subscriber = observer;
    }

    public static <T> void initTask(Subscriber<? super T> observer, Task<T> task) {
        FirebaseRxSubscriber wrapper = new FirebaseRxSubscriber(observer);
        task.addOnSuccessListener(wrapper)
                .addOnFailureListener(wrapper)
                .addOnCompleteListener(wrapper);
    }


    public static <T> void initTaskWithoutCompletedListener(Subscriber<? super T> observer, Task<T> task) {
        FirebaseRxSubscriber handler = new FirebaseRxSubscriber(observer);
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
    public void onFailure(Exception e) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onError(e);
        }
    }
}