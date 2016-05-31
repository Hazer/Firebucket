package com.cremy.shared.mvp;

import com.cremy.shared.data.model.Task;
import com.cremy.shared.mvp.base.BaseMvpView;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface BucketMVP {

    //region View
    interface View extends BaseMvpView {
        void nextCreateTask();
        void loadData();

        void showBucket(ArrayList<Task> _tasks);
        void showBucketEmpty();
        void showError();
    }
    //endregion

    //region Presenter
    interface Presenter extends ValueEventListener {
        void loadBucket();
        void showBucket();
    }
    //endregion

    //region Model
    interface Model {
        HashMap<String, Task> getTasks();
        boolean isEmpty();
        ArrayList<Task> toList();
    }
    //endregion
}
