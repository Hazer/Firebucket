package com.cremy.shared.ui.presenter;

import android.util.Log;

import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.model.Bucket;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.mvp.BucketMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;
import com.cremy.shared.utils.CrashReporter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import javax.inject.Inject;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class BucketPresenter extends BasePresenter<BucketMVP.View>
        implements BucketMVP.Presenter {
    private final static String TAG = "BucketPresenter";

    //region DI
    DataManager dataManager;
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
        this.dataManager.stopBucketListening(this);
        super.detachView();
    }

    @Override
    public void loadBucket() {
        this.dataManager.stopBucketListening(this);
        this.dataManager.startBucketListening(this);
    }

    @Override
    public void removeTask(Task _task) {
        this.dataManager.removeTaskFromDatabase(_task);
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

    //region Firebase ValueEvent
    @Override
    public void onDataChange(DataSnapshot _dataSnapshot) {
        this.checkViewAttached();
        try {
            Log.d(TAG, _dataSnapshot.toString());
            this.model = _dataSnapshot.getValue(Bucket.class);
            this.showBucket();
        } catch (ClassCastException e) {
            e.printStackTrace();
            this.view.showError();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        CrashReporter.log("BucketPresenter: onCancelled | "+ databaseError.getMessage());
        this.view.showMessage(databaseError.getMessage());
    }
    //endregion
}