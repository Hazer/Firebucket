package com.cremy.firebucket.ui.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.cremy.shared.data.model.Task;

public class BucketHeaderViewModel extends BaseObservable {

    private Context context;
    private Task model;
    private int adapterPosition;

    //region Constructor
    public BucketHeaderViewModel(Context context,
                                 Task _task,
                                 int _adapterPosition) {
        this.context = context;
        this.model = _task;
        this.adapterPosition = _adapterPosition;
    }
    //endregion

    //region Content
    public String getHeaderTitle() {
        return this.model.getTitle();
    }
    //endregion

}