package com.example.agnis.mobres;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agnis.mobres.Adapter.RecyclerAdapterKomentar;
import com.example.agnis.mobres.Config.Config;
import com.example.agnis.mobres.Config.UserAPIService;
import com.example.agnis.mobres.Model.ResultKomen;
import com.example.agnis.mobres.Model.ValueKomen;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {

    EditText etKomen;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    ProgressDialog loading;
    private String iduser, id_agen, komen;

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
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        initViews();

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
        Call<ValueKomen> call = api.getJSON3();
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
                    Toast.makeText(FeedbackActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ValueKomen> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(FeedbackActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    //Untuk toolbar kembali ke Home
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tombol_home, menu);//buat notifiikasi
//        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.kembalihome) {
            Intent intentku= new Intent(FeedbackActivity.this,UtamaActivity.class);
            startActivity(intentku);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
