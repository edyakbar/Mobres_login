package com.example.agnis.mobres;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agnis.mobres.Adapter.RecyclerAdapterKomentar;
import com.example.agnis.mobres.Config.Config;
import com.example.agnis.mobres.Config.UserAPIService;
import com.example.agnis.mobres.Model.ResultKomen;
import com.example.agnis.mobres.Model.ValueKomen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KomentarActivity extends AppCompatActivity {

    EditText etKomen;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    ProgressDialog loading;
    String iduser;
    String id_agen;
    String komen;
    String foto;

    Context mContext;
    UserAPIService mApiService;
    private ProgressDialog pDialog;
    private MenuItem mSearchMenuItem;
    private ArrayList<ResultKomen> resultAlls;
    private RecyclerAdapterKomentar adapter;
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formKomentar();
            }
        });
        User user = SharedPrefManager.getInstance(this).getUser();

        Bundle b = getIntent().getExtras();
        id_agen = b.getString("id_agen");
        komen = b.getString("komentar");
        foto = b.getString("foto");


        iduser=(user.getId_user());
        initViews();


    }

    private void formKomentar(){
        dialog = new AlertDialog.Builder(KomentarActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.alert_komentar, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        etKomen    = (EditText) dialogView.findViewById(R.id.etKomen);


        dialog.setNegativeButton("Kirim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tambahKomen();
            }
        });

        dialog.setPositiveButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void tambahKomen(){
        final String komen = etKomen.getText().toString().trim();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("id_agen", id_agen);
        builder.addFormDataPart("id_user", iduser);
        builder.addFormDataPart("id_agen_kartu", id_agen);
        builder.addFormDataPart("komentar", komen);
        MultipartBody requestBody = builder.build();

        UserAPIService getResponse = Config.getRetrofit().create(UserAPIService.class);
        Call<ResponseBody> call = getResponse.komen(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
//                    Log.d("catatan", response.body().string());
                    JSONObject jsonObject = new JSONObject(response.body().string());
//                    Log.d("catatan", String.valueOf(jsonObject));
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(KomentarActivity.this,UtamaActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Gagal Mengambil data1", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Gagal mengambil data2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Respon gagal", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        getData();
    }

    private void getData(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ValueKomen> call = api.getJSON2(id_agen);
        call.enqueue(new Callback<ValueKomen>() {
            @Override
            public void onResponse(Call<ValueKomen> call, Response<ValueKomen> response) {
                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")) {
                    ValueKomen value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterKomentar(resultAlls, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(KomentarActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ValueKomen> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(KomentarActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

}
