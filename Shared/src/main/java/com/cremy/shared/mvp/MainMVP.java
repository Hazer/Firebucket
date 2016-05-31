package com.cremy.shared.mvp;

import com.cremy.shared.data.model.Task;
import com.cremy.shared.mvp.base.BaseMvpView;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface MainMVP {

    //region View
    interface View extends BaseMvpView {
        void nextCreateTask();
        void loadData();

        void showBucket(Task _tasks);
        void showBucketEmpty();
        void showError();
    }
    //endregion

    //region Presenter
    interface Presenter {
        void loadBucket();
    }
    //endregion

    //region Model
    interface Model {

    }
    //endregion
}
