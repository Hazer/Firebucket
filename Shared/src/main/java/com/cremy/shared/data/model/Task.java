package com.cremy.shared.data.model;

import android.content.Context;

import com.cremy.shared.R;
import com.cremy.shared.mvp.CreateTaskMVP;
import com.cremy.shared.utils.CustomDateUtils;
import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Task implements CreateTaskMVP.Model {

    //region Variables
    private String title;
    private TaskPriority priority = new TaskPriority();
    private String deadline = CustomDateUtils.getNow();
    private String displayedDeadline;
    //endregion

    //region Constructors
    public Task() {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }
    public Task(String _title) {
        this.title = _title;
    }
    //endregion

    //region Getters and Setters
    public String getTitle() {
        return title;
    }
    public TaskPriority getPriority() {
        return priority;
    }
    @Override
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
    public String getDeadline() {
        return deadline;
    }
    @Override
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public String getDisplayedDeadline() {
        return displayedDeadline;
    }
    @Override
    public void setDisplayedDeadline(String displayedDeadline) {
        this.displayedDeadline = displayedDeadline;
    }
    @Override
    public void setTitle(String _title) {
        this.title = _title;
    }
    //endregion

    //region Utils

    /**
     * Allows to get the display date from a Calendar Instance
     * Rules:
     * 1. "Today" if same day
     * 2. "Tomorrow" if tomorrow
     * 3. "Wednesday 3" otherwise
     *
     * @param _context
     * @param _calendarInstance
     * @return
     */
    @Exclude
    public static String getDisplayDate(Context _context, Calendar _calendarInstance) {
        if (CustomDateUtils.isToday(_calendarInstance)) {
            return _context.getResources().getString(R.string.today);
        }
        else if (CustomDateUtils.isTomorrow(_calendarInstance)) {
            return _context.getResources().getString(R.string.tomorrow);
        }
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM");
            return dateFormat.format(_calendarInstance.getTime());
        }
    }
    //endregion
}