package com.cremy.shared.ui.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cremy.shared.App;
import com.cremy.shared.R;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.data.model.TaskPriority;
import com.cremy.shared.mvp.CreateTaskMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;
import com.cremy.shared.utils.CrashReporter;
import com.google.firebase.auth.AuthResult;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.Calendar;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class CreateTaskPresenter extends BasePresenter<CreateTaskMVP.View>
        implements CreateTaskMVP.Presenter {
    private final static String TAG = "CreateTaskPresenter";


    //region DI
    DataManager dataManager;
    Context appContext;
    @Inject
    public CreateTaskPresenter(DataManager _dataManager,
                               Context _appContext) {
        dataManager = _dataManager;
        this.appContext = _appContext;
    }
    //endregion

    private Task model = new Task();


    @Override
    public void setTaskPriority(final int _idPriority) {
        this.model.setPriority(new TaskPriority(this.view.getContext(), _idPriority));
    }

    @Override
    public void setTaskDeadline(Calendar _calender) {
        this.model.setDeadline(_calender.getTime().toString());
        this.model.setDisplayedDeadline(Task.getDisplayDate(this.appContext, _calender));
        this.model.setMillisDeadline(_calender.getTimeInMillis());
    }

    @Override
    public void setTaskTitle(final String _title) {
        this.model.setTitle(_title);
    }

    @Override
    public void createTask() {
        Single<Void> createSingle =  this.dataManager.writeTaskInDatabase(this.model);
        // https://github.com/trello/RxLifecycle/issues/39#issuecomment-144194621
        createSingle.toObservable().compose(this.view.bindUntilEvent(ActivityEvent.DESTROY));
        createSingle.subscribe(new SingleSubscriber<Void>() {

            @Override
            public void onSuccess(Void value) {
                onTaskCreatedSuccess();
            }

            @Override
            public void onError(Throwable e) {
                onTaskCreatedFail(e);
            }

        });
    }


    @Override
    public void onTaskCreatedSuccess() {
        checkViewAttached();
        // 1. We tell to the view to go next
        this.view.next();
    }

    @Override
    public void onTaskCreatedFail(Throwable e) {
        checkViewAttached();
        CrashReporter.log("CreateTask: onTaskCreatedFail | "+ e.getMessage());
        this.view.showMessage(this.appContext.getResources().getString(R.string.error_firebase_auth_register));
    }
}