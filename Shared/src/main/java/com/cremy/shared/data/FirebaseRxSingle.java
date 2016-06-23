package com.cremy.shared.data;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * This handler allows to "wrap" the Firebase listeners (OnSuccessListener & OnFailureListener)
 * with a RxJava {@link rx.SingleSubscriber}
 * @param <T>
 */
public class FirebaseRxSingle<T>
        implements OnSuccessListener<T>, OnFailureListener {

    private final SingleSubscriber<? super T> subscriber;

    private FirebaseRxSingle(SingleSubscriber<? super T> observer) {
        this.subscriber = observer;
    }

    private static <T> void initTask(SingleSubscriber<? super T> observer, Task<T> task) {
        FirebaseRxSingle wrapper = new FirebaseRxSingle(observer);
        task.addOnSuccessListener(wrapper)
                .addOnFailureListener(wrapper);
    }

    public static <T> Single<T> getSingle(final Task<T> task) {
        return Single.create(new Single.OnSubscribe<T>() {
            @Override
            public void call(final SingleSubscriber<? super T> subscriber) {
                FirebaseRxSingle.initTask(subscriber, task);
            }
        }).observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io());
    }

    @Override
    public void onSuccess(T res) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onSuccess(res);
        }
    }

    @Override
    public void onFailure(Exception e) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onError(e);
        }
    }
}