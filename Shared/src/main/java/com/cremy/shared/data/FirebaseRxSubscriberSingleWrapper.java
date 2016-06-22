package com.cremy.shared.data;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import rx.SingleSubscriber;

/**
 * This handler allows to wrap the firebase listeners (OnSuccessListener & OnFailureListener)
 * With a RxJava SingleSubscriber
 * @param <T>
 */
public class FirebaseRxSubscriberSingleWrapper<T>
        implements OnSuccessListener<T>,
        OnFailureListener
{

    private final SingleSubscriber<? super T> subscriber;

    private FirebaseRxSubscriberSingleWrapper(SingleSubscriber<? super T> observer) {
        this.subscriber = observer;
    }

    public static <T> void assignOnTask(SingleSubscriber<? super T> observer, Task<T> task) {
        FirebaseRxSubscriberSingleWrapper wrapper = new FirebaseRxSubscriberSingleWrapper(observer);
        task.addOnSuccessListener(wrapper)
                .addOnFailureListener(wrapper);
    }


    public static <T> void assignOnTaskWithoutCompletedListener(SingleSubscriber<? super T> observer, Task<T> task) {
        FirebaseRxSubscriberSingleWrapper handler = new FirebaseRxSubscriberSingleWrapper(observer);
        task.addOnSuccessListener(handler)
                .addOnFailureListener(handler);
    }

    @Override
    public void onSuccess(T res) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onSuccess(res);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onError(e);
        }
    }
}