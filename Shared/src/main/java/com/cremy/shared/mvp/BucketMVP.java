package com.cremy.shared.mvp;

import com.cremy.shared.data.model.Bucket;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.mvp.base.BaseMvpView;
import com.cremy.shared.mvp.base.view.rx.IBaseRxActivity;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface BucketMVP {

    //region View
    interface View extends BaseMvpView, IBaseRxActivity {
        void nextCreateTask();
        void nextLogout();
        void loadData();
        void setUpRecyclerView();

        void showBucket(ArrayList<Task> _tasks);
        void showBucketEmpty();
        void showError();
    }
    //endregion

    //region Presenter
    interface Presenter {
        void loadBucket();
        void onGetBucketSuccess(ArrayList<Task> tasks);
        void onGetBucketFail(Throwable e);
        void showBucket(ArrayList<Task> tasks);


        void removeTask(Task _task);
        void removeTaskTracking(Task _task);

        void logoutUser();
        void logoutUserTracking();
    }
    //endregion

    //region Model
    interface Model {
        HashMap<String, Task> getTasks();
        boolean isEmpty();
        ArrayList<Task> toDisplayedList();
    }
    //endregion
}
