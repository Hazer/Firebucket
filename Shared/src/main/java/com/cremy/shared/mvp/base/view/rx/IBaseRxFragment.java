package com.cremy.shared.mvp.base.view.rx;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * This interface must implemented by {@link BaseRxFragment}
 * Created by remychantenay on 23/05/2016.
 */
public interface IBaseRxFragment {

    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event);

}
