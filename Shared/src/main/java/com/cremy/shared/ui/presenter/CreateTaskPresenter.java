package com.cremy.shared.ui.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cremy.shared.R;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.data.model.TaskPriority;
import com.cremy.shared.mvp.CreateTaskMVP;
import com.cremy.shared.mvp.base.presenter.BasePresenter;
import com.cremy.shared.utils.CrashReporter;

import java.util.Calendar;

import javax.inject.Inject;

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
        this.model.setPriority(new TaskPriority(_idPriority));
    }

    @Override
    public void setTaskDeadline(Calendar _calender) {
        this.model.setDeadline(_calender.getTime().toString());
        this.model.setDisplayedDeadline(Task.getDisplayDate(this.appContext, _calender));
    }

    @Override
    public void setTaskTitle(final String _title) {
        this.model.setTitle(_title);
    }

    @Override
    public void createTask() {
        this.dataManager.writeTaskInDatabase(this.model, this);
    }

    @Override
    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
        if (task.isSuccessful()) {
            this.onTaskCreatedSuccess();
        } else {
            this.onTaskCreatedFail(task.getException());
        }
    }

    @Override
    public void onTaskCreatedSuccess() {
        checkViewAttached();
        // 1. We tell to the view to go next
        this.view.next();
    }

    @Override
    public void onTaskCreatedFail(Exception e) {
        checkViewAttached();
        CrashReporter.log("CreateTask: onTaskCreatedFail | "+ e.getMessage());
        this.view.showMessage(this.appContext.getResources().getString(R.string.error_firebase_auth_register));
    }
}