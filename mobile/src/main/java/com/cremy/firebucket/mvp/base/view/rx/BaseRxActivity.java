package com.cremy.firebucket.mvp.base.view.rx;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cremy.firebucket.mvp.base.view.BaseActivity;
import com.cremy.shared.mvp.base.view.rx.IBaseRxActivity;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * This _MOBILE_ Activity class allows to handle easily its own lifecycle with RxLifecycle
 * This capability is useful in Android, where incomplete subscriptions can cause memory leaks.
 *
 * This class must implement {@link IBaseRxActivity}
 *
 * Created by remychantenay on 18/05/2016.
 */
public abstract class BaseRxActivity
        extends BaseActivity
        implements IBaseRxActivity {

    //region RxLifecycle
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();


    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @NonNull
    @Override
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }
    //endregion

    //region Activity Lifecycle
    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
    }
    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }
    //endregion
}