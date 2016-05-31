package com.cremy.firebucket.ui.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;

public class BucketHeaderViewModel extends BaseObservable {

    private Context context;
    private String headerTitle;
    private int adapterPosition;

    //region Constructor
    public BucketHeaderViewModel(Context context,
                                 String _headerTitle,
                                 int _adapterPosition) {
        this.context = context;
        this.headerTitle = _headerTitle;
        this.adapterPosition = _adapterPosition;
    }
    //endregion

    //region Content
    public String getHeaderTitle() {
        return this.headerTitle;
    }
    //endregion

}