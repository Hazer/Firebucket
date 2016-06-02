package com.cremy.shared.data.model;

import com.cremy.greenrobotutils.library.storage.gson.GSONBaseModel;
import com.cremy.shared.mvp.BucketMVP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class Bucket extends GSONBaseModel
implements BucketMVP.Model{


    //region Variables
    private HashMap<String, Task> tasks = null;
    //endregion

    //region Constructors
    public Bucket() {

    }
    //endregion

    //region Getters and Setters
    @Override
    public HashMap<String, Task> getTasks() {
        return tasks;
    }
    //endregion

    @Override
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    @Override
    public ArrayList<Task> toDisplayedList() {

        // 1. We first re-order the list given by Firebase (ordered by push generated id)
        ArrayList<Task> orderedList = new ArrayList<Task>(this.tasks.values());
        Collections.reverse(orderedList);

        // TODO

        String currentDeadline = new String();
        ListIterator<Task> iterator = orderedList.listIterator();
        while (iterator.hasNext()) {
            final Task tempTask = iterator.next();
            if (tempTask.getDisplayedDeadline()!=null) {
                if (!currentDeadline.equals(tempTask.getDisplayedDeadline())) {
                    currentDeadline = tempTask.getDisplayedDeadline();
                    // We create a header type task (containing only a title)
                    iterator.previous();
                    iterator.add(new Task(currentDeadline));
                }
            }
        }


        return orderedList;
    }

}
