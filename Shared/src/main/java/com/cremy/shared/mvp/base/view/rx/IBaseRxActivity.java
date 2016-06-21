package com.cremy.shared.mvp.base.view.rx;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * This interface must implemented by {@link BaseRxActivity}
 * Created by remychantenay on 23/05/2016.
 */
public interface IBaseRxActivity {

    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event);

}
