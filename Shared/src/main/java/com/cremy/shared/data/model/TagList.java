package com.cremy.shared.data.model;

import com.cremy.greenrobotutils.library.storage.gson.GSONBaseModel;
import com.cremy.shared.mvp.BucketMVP;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class TagList {


    //region Variables
    private HashMap<String, String> tags = null;
    //endregion

    //region Constructors
    public TagList() {

    }
    //endregion

    //region Getters and Setters
    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }
    //endregion

    @Exclude
    public boolean isEmpty() {
        return this.tags.isEmpty();
    }

    @Exclude
    public String[] toDisplayedList() {
        ArrayList<String> list = new ArrayList<String>(this.tags.values());
        String[] strings = list.toArray(new String[this.tags.size()]);
        return strings;
    }

}
