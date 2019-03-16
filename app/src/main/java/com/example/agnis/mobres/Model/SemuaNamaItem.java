package com.example.agnis.mobres.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by edy akbar on 13/09/2018.
 */

public class SemuaNamaItem {
    @SerializedName("nama_provider")
    private String nama_provider;



    public void setNama_provider(String nama_provider){
        this.nama_provider = nama_provider;
    }

    public String getNama_provider(){
        return nama_provider;
    }





    @Override
    public String toString(){
        return
                "SemuaNamaItem{" +
                        "nama_provider = '" + nama_provider + '\'' +

                        "}";
    }
}
