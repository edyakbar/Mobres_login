package com.example.agnis.mobres.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by edy akbar on 13/09/2018.
 */

public class SemuaGBItem {
    @SerializedName("jumlah")
    private String jumlah;

    public void setJumlah(String jumlah){
        this.jumlah = jumlah;
    }

    public String getJumlah(){
        return jumlah;
    }

    @Override
    public String toString(){
        return
                "SemuaNamaItem{" +
                        "jumlah = '" + jumlah + '\'' +

                        "}";
    }
}
