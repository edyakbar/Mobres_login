package com.example.agnis.mobres.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by edy akbar on 12/09/2018.
 */

public class ValueKuota {
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
    private ResultKuota[] result;

    public ResultKuota[] getResult() {
        return result;
    }
}
