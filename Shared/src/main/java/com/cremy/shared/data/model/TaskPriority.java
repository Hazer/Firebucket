package com.cremy.shared.data.model;

import android.content.Context;

import com.cremy.shared.R;
import com.google.firebase.database.Exclude;

/**
 * Nope, I don't want to use Enums :)
 * https://developer.android.com/training/articles/memory.html#Overhead
 */
public class TaskPriority {

    //region Id's
    public final static int PRIORITY_LOW_ID = 0;
    public final static int PRIORITY_NORMAL_ID = 1;
    public final static int PRIORITY_HIGH_ID = 2;
    public final static int PRIORITY_CRUCIAL_ID = 3;
    //endregion

    //region Variables
    private int id = PRIORITY_NORMAL_ID;
    private String label = "Normal";
    //endregion

    //region Constructors
    public TaskPriority() {
    }
    public TaskPriority(Context _context, int _id) {
        this.id = _id;
        this.label = getResourceLabel(_context, _id);
    }
    //endregion

    //region Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String _label) {
        this.label = _label;
    }

    @Exclude
    public int getColor(Context _context) {
        switch (this.id) {
            case PRIORITY_LOW_ID:
                return _context.getResources().getColor(R.color.taskPriorityLow);
            case PRIORITY_HIGH_ID:
                return _context.getResources().getColor(R.color.taskPriorityHigh);
            case PRIORITY_CRUCIAL_ID:
                return _context.getResources().getColor(R.color.taskPriorityCrucial);
            default:
                return _context.getResources().getColor(R.color.taskPriorityNormal);
        }
    }

    public static String getResourceLabel(Context _context, int _idPriority) {
        String[] labels = _context.getResources().getStringArray(R.array.task_priority_labels);
        return labels[_idPriority];
    }

    //endregion
}