package com.cremy.shared.data.model;

/**
 * No, I don't want to use Enums :)
 */
public class TaskPriority {

    //region Id's
    public final static int PRIORITY_LOW_ID = 0;
    public final static int PRIORITY_NORMAL_ID = 1;
    public final static int PRIORITY_HIGH_ID = 3;
    public final static int PRIORITY_CRUCIAL_ID = 4;
    //endregion

    //region Labels
    public final static String PRIORITY_LOW_LABEL = "Low";
    public final static String PRIORITY_NORMAL_LABEL = "Normal";
    public final static String PRIORITY_HIGH_LABEL = "High";
    public final static String PRIORITY_CRUCIAL_LABEL = "Crucial";
    //endregion

    //region Variables
    private int id;
    private String label;
    //endregion

    //region Constructors
    public TaskPriority() {
        // Default constructor required for calls to DataSnapshot.getValue(TaskPriority.class)
        this.id = PRIORITY_NORMAL_ID;
        this.label = PRIORITY_NORMAL_LABEL;
    }
    public TaskPriority(int _id) {
        this.id = _id;
    }
    public TaskPriority(int _id, String _label) {
        this.id = _id;
        this.label = _label;
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

    //endregion
}