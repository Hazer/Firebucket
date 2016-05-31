package com.cremy.shared.mvp;

import com.cremy.shared.data.model.TaskPriority;
import com.cremy.shared.mvp.base.BaseMvpView;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.Calendar;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface CreateTaskMVP {

    //region View
    interface View extends BaseMvpView {
        void createTask();
        void next();

        boolean isTaskTitleValid();
        void setUpDatePickerData();

        void updatePresenterTaskDeadline(Calendar _calendar);
        void updateViewTaskDeadline(Calendar _calendar);
    }
    //endregion

    //region Presenter
    interface Presenter extends OnCompleteListener<Void> {
        void setTaskPriority(int _idPriority);
        void setTaskDeadline(Calendar _calendar);
        void setTaskTitle(String _title);

        void createTask();
        void onTaskCreatedSuccess();
        void onTaskCreatedFail(Exception e);
    }
    //endregion

    //region Model
    interface Model {
        void setPriority(TaskPriority _taskPriority);
        void setDeadline(String deadline);
        void setTitle(String title);
        void setDisplayedDeadline(String displayedDeadline);
    }
    //endregion
}
