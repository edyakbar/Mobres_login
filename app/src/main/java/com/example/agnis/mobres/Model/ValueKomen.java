package com.example.agnis.mobres.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rizzabas on 17/08/2018.
 */

public class ValueKomen {
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("result")
    @Expose
    private ResultKomen[] result;

    public ResultKomen[] getResult() {
        return result;
    }
}
