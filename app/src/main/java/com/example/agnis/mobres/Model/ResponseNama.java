package com.example.agnis.mobres.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by edy akbar on 13/09/2018.
 */

public class ResponseNama {
    @SerializedName("result")
    private List<SemuaNamaItem> result;


    @SerializedName("status")
    private String status;

    public void setResult(List<SemuaNamaItem> result){
        this.result = result;
    }

    public List<SemuaNamaItem> getResult(){
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
                "ResponseNama{" +
                        "result = '" + result + '\'' +

                        ",status = '" + status + '\'' +
                        "}";
    }

}
