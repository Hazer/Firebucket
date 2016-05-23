package com.cremy.shared.data.model;

import com.cremy.greenrobotutils.library.storage.gson.GSONBaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by remychantenay on 23/05/2016.
 */
public class TORENAMEMODEL extends GSONBaseModel {

    //region Variables
    @SerializedName("id") private String id = null;
    //endregion

    //region Getters & Settings

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //endregion
}
