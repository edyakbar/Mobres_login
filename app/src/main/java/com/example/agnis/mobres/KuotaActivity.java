package com.example.agnis.mobres;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.agnis.mobres.Adapter.RecyclerAdapterKuota;
import com.example.agnis.mobres.Adapter.RecyclerAdapterListAll;
import com.example.agnis.mobres.Config.Config;
import com.example.agnis.mobres.Config.UserAPIService;
import com.example.agnis.mobres.Model.ResponseGB;
import com.example.agnis.mobres.Model.ResponseNama;
import com.example.agnis.mobres.Model.ResultAll;
import com.example.agnis.mobres.Model.ResultKuota;
import com.example.agnis.mobres.Model.SemuaGBItem;
import com.example.agnis.mobres.Model.SemuaNamaItem;
import com.example.agnis.mobres.Model.Value;
import com.example.agnis.mobres.Model.ValueKuota;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KuotaActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ProgressDialog pDialog;
    private MenuItem mSearchMenuItem;
    private ArrayList<ResultKuota> resultAlls;
    private RecyclerAdapterKuota adapter;
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.spinnerNama)
    Spinner pilihan;
    @BindView(R.id.spinerGB)
    Spinner pilihanpGB;
    @BindView(R.id.cari)
    Button cari;

    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Daftar Kuota");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initViews();
        initSpinnerNama();
        initSpinnerGB();
        // isi_dropdown();

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plhNama = pilihan.getSelectedItem().toString();
                String plhGB= pilihanpGB.getSelectedItem().toString();
                if (plhNama.equals("Semua")&& plhGB.equals("Semua")){
                    getData();
                    return;
                }else if (plhGB.equals("Semua")){
                    getDatabyNama();
                    return;
                }else if (plhNama.equals("Semua")){
                    getDatabyJumlah();
                    return;

                }else {
                    getDatabySemua();
                    return;
                }
            }
        });
    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        getData();
    }

    private void getData(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ValueKuota> call = api.get_all_kuota();
        call.enqueue(new Callback<ValueKuota>() {
            @Override
            public void onResponse(Call<ValueKuota> call, Response<ValueKuota> response) {
                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")) {
                    ValueKuota value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterKuota(resultAlls, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(KuotaActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ValueKuota> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(KuotaActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }


    private void getDatabyNama(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ValueKuota> call = api.get_nama(pilihan.getSelectedItem().toString());
        call.enqueue(new Callback<ValueKuota>() {
            @Override
            public void onResponse(Call<ValueKuota> call, Response<ValueKuota> response) {
                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")) {
                    ValueKuota value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterKuota(resultAlls, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(KuotaActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ValueKuota> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(KuotaActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    private void getDatabyJumlah(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ValueKuota> call = api.get_jumlah(pilihanpGB.getSelectedItem().toString());
        call.enqueue(new Callback<ValueKuota>() {
            @Override
            public void onResponse(Call<ValueKuota> call, Response<ValueKuota> response) {
                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")) {
                    ValueKuota value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterKuota(resultAlls, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(KuotaActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ValueKuota> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(KuotaActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    private void getDatabySemua(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ValueKuota> call = api.get_semua(pilihan.getSelectedItem().toString(),pilihanpGB.getSelectedItem().toString());
        call.enqueue(new Callback<ValueKuota>() {
            @Override
            public void onResponse(Call<ValueKuota> call, Response<ValueKuota> response) {
                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")) {
                    ValueKuota value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterKuota(resultAlls, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(KuotaActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ValueKuota> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(KuotaActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }


    private void initSpinnerNama(){
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ResponseNama> call = api.get_all_nama_provider();
        call.enqueue(new Callback<ResponseNama>() {
            @Override
            public void onResponse(Call<ResponseNama> call, Response<ResponseNama> response) {
                if (response.isSuccessful()) {
                    // loading.dismiss();
                    List<SemuaNamaItem> semuadosenItems = response.body().getResult();
                    List<String> listSpinner = new ArrayList<String>();

                    for (int i = semuadosenItems.size()-1; i >=0; i--){
                        listSpinner.add(semuadosenItems.get(i).getNama_provider());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(KuotaActivity.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pilihan.setAdapter(adapter);

                    // initSpinnerDesa();

                } else {
                    //  loading.dismiss();
                    Toast.makeText(KuotaActivity.this, "Gagal Mengambil Data ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseNama> call, Throwable t) {
                // loading.dismiss();
                Toast.makeText(KuotaActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerGB(){
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ResponseGB> call = api.get_all_jumlah_kuota();
        call.enqueue(new Callback<ResponseGB>() {
            @Override
            public void onResponse(Call<ResponseGB> call, Response<ResponseGB> response) {
                if (response.isSuccessful()) {
                    // loading.dismiss();
                    List<SemuaGBItem> semuadosenItems = response.body().getResult();
                    List<String> listSpinner = new ArrayList<String>();

                    for (int i = semuadosenItems.size()-1; i >=0; i--){
                        listSpinner.add(semuadosenItems.get(i).getJumlah());
                      }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(KuotaActivity.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pilihanpGB.setAdapter(adapter);
                    // initSpinnerDesa();

                } else {
                    //  loading.dismiss();
                    Toast.makeText(KuotaActivity.this, "Gagal Mengambil Data ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGB> call, Throwable t) {
                // loading.dismiss();
                Toast.makeText(KuotaActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                adapter.setFilter(resultAlls);
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true; // Return true to expand action view
            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<ResultKuota> filteredModelList = filter(resultAlls, newText);

        adapter.setFilter(filteredModelList);
        return true;
    }

    private ArrayList<ResultKuota> filter(ArrayList<ResultKuota> models, String query) {
        query = query.toLowerCase();final ArrayList<ResultKuota> filteredModelList = new ArrayList<>();
        for (ResultKuota model : models) {
            final String text = model.getNama_provider().toLowerCase();
            final String text2 = model.getJumlah().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }else if (text2.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
