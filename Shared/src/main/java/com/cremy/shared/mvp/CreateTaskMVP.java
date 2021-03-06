package com.cremy.shared.mvp;

import android.speech.RecognitionListener;

import com.cremy.shared.data.model.TagList;
import com.cremy.shared.data.model.TaskPriority;
import com.cremy.shared.mvp.base.BaseMvpView;
import com.cremy.shared.mvp.base.view.rx.IBaseRxActivity;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.Calendar;

/**
 * Created by remychantenay on 08/05/2016.
 */
public interface CreateTaskMVP {

    //region View
    interface View extends BaseMvpView, IBaseRxActivity, RecognitionListener {
        void createTask();
        void next();

        boolean isTaskTitleValid();
        void setUpDatePickerData();

        void updatePresenterTaskDeadline(Calendar _calendar);
        void updateViewTaskDeadline(Calendar _calendar);

        void updateViewTaskPriority(int idPriority);

        void updateViewTaskTag(String _tag);

        void displayTagListAlertDialog(String[] tagList);

        void startVoiceRecognition();
    }
    //endregion

    //region Presenter
    interface Presenter {
        void setTaskPriority(int _idPriority);
        void setTaskDeadline(Calendar _calendar);
        void setTaskTitle(String _title);
        void setTaskTag(String _tag);

        void createTask();

        void onTaskCreatedSuccess();
        void onTaskCreatedFail(Throwable e);

        void onTaskCreatedSuccessTracking();
        void onTaskCreatedFailTracking(Throwable e);

        void getTagList();
        void onGetTagListSuccess(TagList tagList);
        void onGetTagListFail(Throwable e);
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
