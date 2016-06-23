package com.cremy.firebucket.ui.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.cremy.firebucket.R;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.data.model.TaskPriority;

public class BucketTaskViewModel extends BaseObservable {

    private Context context;
    private Task model;
    private int adapterPosition;

    //region Constructor
    public BucketTaskViewModel(Context context,
                               Task _task,
                               int _adapterPosition) {
        this.context = context;
        this.model = _task;
        this.adapterPosition = _adapterPosition;
    }
    //endregion

    //region Content
    public String getTaskTitle() {
        return this.model.getTitle();
    }

    public int getTaskPriorityColor() {
        return this.model.getPriority().getColor(this.context);
    }
    public String getTaskPriorityLabel() {
        return this.model.getPriority().getLabel();
    }
    public String getTaskTag() {
        return this.model.getTag();
    }
    public boolean hasTag() {
        return this.model.getTag()!=null;
    }
    //endregion
}