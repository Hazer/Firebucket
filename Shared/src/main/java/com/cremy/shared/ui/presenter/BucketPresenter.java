package com.cremy.shared.ui.presenter;

import android.os.Bundle;
import android.util.Log;

import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.model.Bucket;
import com.cremy.shared.data.model.TagList;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.exceptions.FirebaseRxDataException;
import com.cremy.shared.mvp.BucketMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;
import com.cremy.shared.utils.CrashReporter;
import com.cremy.shared.utils.rx.RxUtil;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;
import java.util.Observer;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class BucketPresenter extends BasePresenter<BucketMVP.View>
        implements BucketMVP.Presenter {
    private final static String TAG = "BucketPresenter";

    //region DI
    DataManager dataManager;

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    @Inject
    public BucketPresenter(DataManager _dataManager) {
        dataManager = _dataManager;
    }
    //endregion

    Subscription bucketSubscription;

    @Override
    public void detachView() {
        if (this.bucketSubscription!=null) {
            this.bucketSubscription.unsubscribe();
        }
        super.detachView();
    }

    @Override
    public void loadBucket() {
        bucketSubscription = this.dataManager.getBucket()
                // We use the Map operator to add the feed headers before emitting the result
                .map(new Func1<Bucket, ArrayList<Task>>() {
            @Override
            public ArrayList<Task> call(Bucket bucket) {
                return bucket.toDisplayedList();
            }
        })
        // We use the Compose operator to add the schedulers
        // We make sure we use it after using the map operator
        .compose(RxUtil.<ArrayList<Task>>applySchedulers())
        .subscribe(new Subscriber<ArrayList<Task>>() {
            @Override
            public void onCompleted() {
                // Nothing to do here
            }

            @Override
            public void onError(Throwable e) {
                onGetBucketFail(e);
            }

            @Override
            public void onNext(ArrayList<Task> tasks) {
                onGetBucketSuccess(tasks);
            }
        });
    }

    @Override
    public void onGetBucketSuccess(ArrayList<Task> tasks) {
        this.checkViewAttached();
        this.showBucket(tasks);
    }

    @Override
    public void onGetBucketFail(Throwable e) {
        checkViewAttached();
        if (e instanceof FirebaseRxDataException) {
            FirebaseRxDataException firebaseRxDataException = (FirebaseRxDataException) e;
            // -3 Being Permission denied, it will happen on user logout
            if (firebaseRxDataException.getError().getCode()==-3) {
                return;
            }
        }

        CrashReporter.log("BucketPresenter: onCancelled | "+ e.getMessage());
        this.view.showMessage(e.getMessage());
    }

    @Override
    public void removeTask(Task _task) {
        this.removeTaskTracking(_task);

        this.dataManager.removeTaskFromDatabase(_task);
    }

    @Override
    public void removeTaskTracking(Task _task) {
        Bundle bundle = new Bundle();
        bundle.putString("task_title", _task.getTitle());
        firebaseAnalytics.logEvent("remove_task", bundle);
    }

    @Override
    public void logoutUser() {
        this.logoutUserTracking();

        this.dataManager.logoutUser();
    }

    @Override
    public void logoutUserTracking() {
        firebaseAnalytics.logEvent("logout", null);
    }

    @Override
    public void showBucket(ArrayList<Task> tasks) {
        if (this.isViewAttached()) {
            if (tasks != null) {
                if (!tasks.isEmpty()) {
                    this.view.showBucket(tasks);
                } else {
                    this.view.showBucketEmpty();
                }
            }
            else {
                this.view.showBucketEmpty();
            }
        }
    }
}