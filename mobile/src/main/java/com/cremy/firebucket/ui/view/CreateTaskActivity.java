package com.cremy.firebucket.ui.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.cremy.firebucket.R;
import com.cremy.firebucket.mvp.base.view.BaseActivity;
import com.cremy.greenrobotutils.library.ui.ActivityUtils;
import com.cremy.greenrobotutils.library.ui.SnackBarUtils;
import com.cremy.greenrobotutils.library.util.KeyboardUtils;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.data.model.TaskPriority;
import com.cremy.shared.mvp.CreateTaskMVP;
import com.cremy.shared.ui.presenter.CreateTaskPresenter;
import com.cremy.shared.utils.CustomDateUtils;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateTaskActivity extends BaseActivity implements
        CreateTaskMVP.View,
DatePickerDialog.OnDateSetListener{
    public ProgressDialog progress;

    //region View binding
    @BindView(R.id.rootViewCreateTask)
    CoordinatorLayout rootViewCreateTask;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.createTaskTitleTextInputLayout)
    TextInputLayout createTaskTitleTextInputLayout;

    @BindView(R.id.createTaskOptionItemDeadlineSubtitle)
    TextView createTaskOptionItemDeadlineSubtitle;

    @BindView(R.id.createTaskOptionItemPrioritySubtitle)
    TextView createTaskOptionItemPrioritySubtitle;
    //endregion

    //region Date
    int startYear;
    int startMonth;
    int startDay;
    //endregion

    //region View events
    @OnClick(R.id.fabCreateTask)
    public void clickFabCreateTask() {
        if (this.isTaskTitleValid()) {
            this.presenter.setTaskTitle(createTaskTitleTextInputLayout.getEditText().getText().toString());
            this.createTask();
        } else {
            this.showMessage(getResources().getString(R.string.error_create_task_invalid_title));
        }
    }

    @OnClick(R.id.createTaskOptionItemDeadline)
    public void clickCreateTaskOptionItemDeadline() {
        DatePickerDialog dialog = new DatePickerDialog(this, this, startYear, startMonth, startDay);
        dialog.show();
    }

    @OnClick(R.id.createTaskOptionItemPriority)
    public void clickCreateTaskOptionItemPriority() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this, 5);
        ad.setTitle(getResources().getString(R.string.create_task_option_item_priority_title));
        ad.setCancelable(true);
        ad.setItems(R.array.task_priority_labels, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int idPriority) {
                presenter.setTaskPriority(idPriority);
                updateViewTaskPriority(idPriority);
            }
        });
        ad.show();
    }
    //endregion

    //region DI
    @Inject
    CreateTaskPresenter presenter;
    @Override
    public void injectDependencies() {
        activityComponent().inject(this);
    }
    //endregion

    //region Presenter
    public void attachToPresenter() {
        this.presenter.attachView(this);
    }
    public void detachFromPresenter() {
        this.presenter.detachView();
    }
    //endregion

    /**
     * Allows to start this activity
     * @param _context
     */
    public static void startMe(Context _context) {
        Intent intent = new Intent(_context, CreateTaskActivity.class);
        _context.startActivity(intent);
    }

    //region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        ButterKnife.bind(this);

        this.injectDependencies();
        this.attachToPresenter();

        this.getExtras(getIntent());
        this.setUpToolbar();

        this.setUpDatePickerData();
    }

    @Override
    protected void onDestroy() {
        this.detachFromPresenter();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.closeActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion


    @Override
    public void onLandscape() {

    }

    @Override
    public void onPortrait() {

    }

    @Override
    public void getExtras(Intent _intent) {

    }

    @Override
    public void closeActivity() {
        this.finish();
    }

    @Override
    public void setUpToolbar() {
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setTitle("");
        ActivityUtils.setToolbarCustomWhiteBackArrow(this, this.getSupportActionBar());
    }

    @Override
    public Fragment getAttachedFragment(int _id) {
        return getSupportFragmentManager().findFragmentById(_id);
    }

    @Override
    public void showLoading() {
        progress = ProgressDialog.show(this, getResources().getString(R.string.general_progress_dialog_title), getResources().getString(R.string.general_progress_dialog_content), true);
        KeyboardUtils.hideKeyboard(this, this.createTaskTitleTextInputLayout.getEditText());
    }

    @Override
    public void hideLoading() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    @Override
    public void showNoNetwork() {
        SnackBarUtils.showActionSnackbar(this.rootViewCreateTask,
                getResources().getString(R.string.no_network),
                getResources().getString(R.string.retry),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createTask();
                    }
                }
        );
    }

    @Override
    public void showMessage(String _message) {
        SnackBarUtils.showSimpleSnackbar(this.rootViewCreateTask, _message);
    }

    @Override
    public void createTask() {
        this.showLoading();
        presenter.createTask();
    }

    @Override
    public void next() {
        this.hideLoading();
        this.closeActivity();
    }

    @Override
    public boolean isTaskTitleValid() {
        final String title = this.createTaskTitleTextInputLayout.getEditText().getText().toString();
        if (title.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        if (!CustomDateUtils.isBeforeToday(calendar)) {
            this.startYear = year;
            this.startMonth = monthOfYear;
            this.startDay = dayOfMonth;

            // We update both the view and presenter
            this.updatePresenterTaskDeadline(calendar);
            this.updateViewTaskDeadline(calendar);
        } else {
            this.showMessage(getResources().getString(R.string.error_create_task_date_picking_too_early));
        }
    }

    @Override
    public void setUpDatePickerData() {
        Calendar calendar = Calendar.getInstance();
        this.startYear = calendar.get(Calendar.YEAR);
        this.startMonth = calendar.get(Calendar.MONTH);
        this.startDay = calendar.get(Calendar.DAY_OF_MONTH);

        // We update both the view and presenter
        this.updatePresenterTaskDeadline(calendar);
        this.updateViewTaskDeadline(calendar);
    }

    @Override
    public void updatePresenterTaskDeadline(Calendar _calendar) {
        this.presenter.setTaskDeadline(_calendar);
    }

    @Override
    public void updateViewTaskDeadline(Calendar _calendar) {
        final String displayedDeadline = Task.getDisplayDate(this, _calendar);
        this.createTaskOptionItemDeadlineSubtitle.setText(displayedDeadline);
    }

    @Override
    public void updateViewTaskPriority(int _idPriority) {
        this.createTaskOptionItemPrioritySubtitle.setText(TaskPriority.getResourceLabel(CreateTaskActivity.this, _idPriority));
    }

}