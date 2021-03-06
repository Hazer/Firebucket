package com.cremy.firebucket.ui.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.cremy.firebucket.R;
import com.cremy.firebucket.mvp.base.view.BaseActivity;
import com.cremy.firebucket.mvp.base.view.rx.BaseRxActivity;
import com.cremy.greenrobotutils.library.device.LocaleUtil;
import com.cremy.greenrobotutils.library.permission.PermissionHelper;
import com.cremy.greenrobotutils.library.ui.ActivityUtils;
import com.cremy.greenrobotutils.library.ui.SnackBarUtils;
import com.cremy.greenrobotutils.library.util.KeyboardUtils;
import com.cremy.greenrobotutils.library.util.NetworkUtils;
import com.cremy.shared.data.model.TagList;
import com.cremy.shared.data.model.Task;
import com.cremy.shared.data.model.TaskPriority;
import com.cremy.shared.mvp.CreateTaskMVP;
import com.cremy.shared.ui.presenter.CreateTaskPresenter;
import com.cremy.shared.utils.CustomDateUtils;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateTaskActivity extends BaseRxActivity implements
        CreateTaskMVP.View,
DatePickerDialog.OnDateSetListener{
    private final static String TAG = "CreateTaskActivity";
    public ProgressDialog progress;
    private SpeechRecognizer speechRecognizer;

    //region View binding
    @BindView(R.id.rootViewCreateTask)
    CoordinatorLayout rootViewCreateTask;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.createTaskTitleTextInputLayout)
    TextInputLayout createTaskTitleTextInputLayout;

    @BindView(R.id.createTaskVoiceRecognitionButton)
    ImageView createTaskVoiceRecognitionButton;

    @BindView(R.id.createTaskOptionItemDeadlineSubtitle)
    TextView createTaskOptionItemDeadlineSubtitle;

    @BindView(R.id.createTaskOptionItemPrioritySubtitle)
    TextView createTaskOptionItemPrioritySubtitle;

    @BindView(R.id.createTaskOptionItemTagsSubtitle)
    TextView createTaskOptionItemTagsSubtitle;
    //endregion

    //region Date
    int startYear;
    int startMonth;
    int startDay;
    //endregion

    //region View events
    @OnClick(R.id.createTaskVoiceRecognitionButton)
    public void clickCreateTaskVoiceRecognitionButton() {
        if (PermissionHelper.isRecordAudioPermissionGranted(this)) {
            this.startVoiceRecognition();
        }
        else {
            PermissionHelper.requestRecordAudioPermission(this);
        }
    }

    @OnClick(R.id.fabCreateTask)
    public void clickFabCreateTask() {

        if (NetworkUtils.isNetworkEnabled(this)) {
            if (this.isTaskTitleValid()) {
                this.presenter.setTaskTitle(createTaskTitleTextInputLayout.getEditText().getText().toString());
                this.createTask();
            } else {
                this.showMessage(getResources().getString(R.string.error_create_task_invalid_title));
            }
        } else {
            this.showNoNetwork();
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
            public void onClick(DialogInterface dialog, int index) {
                presenter.setTaskPriority(index);
                updateViewTaskPriority(index);
            }
        });
        ad.show();
    }
    @OnClick(R.id.createTaskOptionItemTags)
    public void clickCreateTaskOptionItemTags() {
        this.showLoading();
        this.presenter.getTagList();
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
                        clickFabCreateTask();
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

    @Override
    public void updateViewTaskTag(String _tag) {
        this.createTaskOptionItemTagsSubtitle.setText(_tag);
    }

    @Override
    public void displayTagListAlertDialog(final String[] tagList) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this, 5);
        ad.setTitle(getResources().getString(R.string.create_task_option_item_tags_title));
        ad.setCancelable(true);
        ad.setItems(tagList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int index) {
                presenter.setTaskTag(tagList[index]);
                updateViewTaskTag(tagList[index]);
            }
        });
        ad.show();
    }

    //region Voice Recognition
    @Override
    public void startVoiceRecognition() {

        // We start the speech recognizer intent
        this.speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        this.speechRecognizer.setRecognitionListener(this);

        String deviceLocale = getResources().getConfiguration().locale.toString();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, deviceLocale);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

        this.speechRecognizer.startListening(intent);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        // We start to animate the button
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5F, 1.2F, 0.5F, 1.2F, 1, 0.5F, 1, 0.5F);
        scaleAnimation.setDuration(500);
        scaleAnimation.setRepeatMode(ScaleAnimation.REVERSE);
        scaleAnimation.setRepeatCount(10);
        this.createTaskVoiceRecognitionButton.startAnimation(scaleAnimation);
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        this.createTaskVoiceRecognitionButton.clearAnimation();
    }

    @Override
    public void onError(int error) {
        if (error!=SpeechRecognizer.ERROR_SPEECH_TIMEOUT) {
            SnackBarUtils.showSimpleSnackbar(this.rootViewCreateTask, getResources().getString(R.string.error_create_task_voice_recognition_error_code, error));
        }
    }

    @Override
    public void onResults(Bundle results) {
        Log.d(TAG, "onResults " + results);
        ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (data!=null && data.size()>0) {
            Log.e(TAG, "Result: "+data.get(0));
            this.createTaskTitleTextInputLayout.getEditText().setText(data.get(0));
        }
        else {
            SnackBarUtils.showSimpleSnackbar(this.rootViewCreateTask, getResources().getString(R.string.error_create_task_voice_recognition));
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
    //endregion

    //region RECORD_AUDIO permission related
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.startVoiceRecognition();
        } else {
            // Nothing to do
        }
        return;
    }
    //endregion
}