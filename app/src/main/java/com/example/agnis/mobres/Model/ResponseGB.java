package com.example.agnis.mobres.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by edy akbar on 13/09/2018.
 */

public class ResponseGB {
    @SerializedName("result")
    private List<SemuaGBItem> result;


    @SerializedName("status")
    private String status;

    public void setResult(List<SemuaGBItem> result){
        this.result = result;
    }

    public List<SemuaGBItem> getResult(){
        return result;
    }


    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    @Override
    public String toString(){
        return
                "ResponseGB{" +
                        "result = '" + result + '\'' +

                        ",status = '" + status + '\'' +
                        "}";
    }

}
