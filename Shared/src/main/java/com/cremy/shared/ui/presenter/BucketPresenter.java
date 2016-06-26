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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.trello.rxlifecycle.ActivityEvent;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;

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

    //region Model
    BucketMVP.Model model;
    //endregion


    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void loadBucket() {
        Observable<Bucket> bucketObservable =  this.dataManager.getBucket();
        // https://github.com/trello/RxLifecycle/issues/39#issuecomment-144194621
        bucketObservable.compose(this.view.bindUntilEvent(ActivityEvent.DESTROY));
        bucketObservable.subscribe(new Subscriber<Bucket>() {
            @Override
            public void onCompleted() {
                // Nothing to do here
            }

            @Override
            public void onError(Throwable e) {
                onGetBucketFail(e);
            }

            @Override
            public void onNext(Bucket bucket) {
                onGetBucketSuccess(bucket);
            }

        });
    }

    @Override
    public void onGetBucketSuccess(Bucket bucket) {
        this.checkViewAttached();
        this.model = bucket;
        this.showBucket();
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
    public void showBucket() {
        if (this.isViewAttached()) {
            if (this.model != null) {
                if (!this.model.isEmpty()) {
                    this.view.showBucket(this.model.toDisplayedList());
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