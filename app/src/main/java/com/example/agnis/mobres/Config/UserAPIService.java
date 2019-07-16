package com.example.agnis.mobres.Config;

import android.renderscript.Sampler;

import com.example.agnis.mobres.Model.ResponseGB;
import com.example.agnis.mobres.Model.ResponseNama;
import com.example.agnis.mobres.Model.Value;
import com.example.agnis.mobres.Model.ValueKomen;
import com.example.agnis.mobres.Model.ValueKuota;
import com.example.agnis.mobres.Model.ValueProvider;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Rizzabas on 06/07/2018.
 */

public interface UserAPIService {
    @GET("get_all")
    Call<Value> getJSON();
//
//    @GET("get_bidan")
//    Call<ValueBidan> getJSON1();
//
    @GET("get_feed")
    Call<ValueKomen> getJSON3();

    @GET("get_all_kuota")
    Call<ValueKuota> get_all_kuota();

    @POST("update_profil")
    Call<ResponseBody> update_profil(@Body RequestBody file);

    @FormUrlEncoded
    @POST("get_nama")
    Call<ValueKuota> get_nama(@Field("nama_provider") String nama_provider);

    @FormUrlEncoded
    @POST("get_jumlah")
    Call<ValueKuota> get_jumlah(@Field("jumlah") String jumlah);

    @FormUrlEncoded
    @POST("get_semua")
    Call<ValueKuota> get_semua(@Field("nama_provider") String nama_provider,
                               @Field("jumlah") String jumlah);


    @GET("get_all_nama_provider")
    Call<ResponseNama> get_all_nama_provider();


    @GET("get_all_jumlah_kuota")
    Call<ResponseGB> get_all_jumlah_kuota();

    @GET("get_all_kuota")
    Call<ValueProvider> get_all_kuota2();

    @FormUrlEncoded
    @POST("get_kuota")
    Call<ValueProvider> getKuota(@Field("id_agen_kartu") String id_agen_kartu);

    @FormUrlEncoded
    @POST("get_agen_kuota")
    Call<ValueProvider> get_agen_kuota(@Field("nama_provider") String nama_provider,
                                       @Field("jumlah") String jumlah);

    @FormUrlEncoded
    @POST("getTerdekat")
    Call<ValueProvider> getTerdekat2(@Field("lati") String lati,
                                       @Field("longi") String longi,
                                     @Field("nama_provider") String nama_provider,
                                     @Field("jumlah") String jumlah);

    @FormUrlEncoded
    @POST("get_komen")
    Call<ValueKomen> getJSON2(@Field("id_agen_kartu") String id_agen_kartu);

    @POST("login")
    Call<ResponseBody> login(@Body RequestBody file);

    @POST("signup")
    Call<ResponseBody> signup(@Body RequestBody file);

    @POST("feedback")
    Call<ResponseBody> komen(@Body RequestBody file);


    @POST("getTerdekat")
    Call<ResponseBody> getTerdekat(@Body RequestBody file);

    @FormUrlEncoded
    @POST("change_passwd")
    Call<ResponseBody> change_passwd(@Field("id_user") String id_user,
                                     @Field("password") String password,
                                     @Field("new_passwd") String new_passwd);
}

