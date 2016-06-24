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
public final class TagList {


    //region Variables
    private HashMap<String, String> tags = null;
    //endregion

    //region Constructors
    public TagList() {

    }
    //endregion

    public boolean isEmpty() {
        return this.tags.isEmpty();
    }

    public CharSequence[] toDisplayedList() {
        ArrayList<String> list = new ArrayList<String>(this.tags.values());
        return (CharSequence[]) list.toArray();
    }

}
