package com.cremy.shared.data.model;

import com.cremy.greenrobotutils.library.storage.gson.GSONBaseModel;
import com.cremy.shared.mvp.BucketMVP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
        super();
        this.tasks = new HashMap<String, Task>();
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
    public ArrayList<Task> toList() {
        return (ArrayList<Task>) this.tasks.values();
    }

}
