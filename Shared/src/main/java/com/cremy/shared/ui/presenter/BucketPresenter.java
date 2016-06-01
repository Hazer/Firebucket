package com.cremy.shared.ui.presenter;

import android.util.Log;

import com.cremy.shared.data.DataManager;
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
    private final static String TAG = "MainPresenter";

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
        this.dataManager.removeBucketListener(this);
        super.detachView();
    }

    @Override
    public void loadBucket() {
        this.dataManager.addBucketListener(this);
    }

    @Override
    public void showBucket() {
        if (this.isViewAttached()) {
            if (this.model != null) {
                if (!this.model.isEmpty()) {
                    this.view.showBucket(this.model.toList());
                } else {
                    this.view.showBucketEmpty();
                }
            }
            else {
                this.view.showError();
            }
        }
    }

    //region Firebase ValueEvent
    @Override
    public void onDataChange(DataSnapshot _dataSnapshot) {
        this.checkViewAttached();
        try {
            Log.d(TAG, _dataSnapshot.toString());
            // TODO
            //this.model = _dataSnapshot.getValue(Bucket.class);

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