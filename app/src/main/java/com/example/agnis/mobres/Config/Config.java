package com.example.agnis.mobres.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rizzabas on 06/07/2018.
 */

public class Config {

//    public static final String BASE_URL = "https://adminagenkartu.000webhostapp.com/";
//    public static final String URL = "http://adminagenkartu.000webhostapp.com/Api/";
    public static final String BASE_URL = "http://192.168.1.6/adminagen";
    public static final String URL = "http://192.168.1.6/adminagen/Api/";
    public static final String URL_IMG_AGEN = BASE_URL + "images/";
    public static final String URL_IMG_PROVIDER = BASE_URL + "images/";
    public static final String URL_IMG_PROFIL = BASE_URL + "images/";

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    private static Retrofit retrofit = null;
}
